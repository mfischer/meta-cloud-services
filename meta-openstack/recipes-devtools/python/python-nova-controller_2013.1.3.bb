include python-nova.inc

PR = "r0"

FILESEXTRAPATHS := "${THISDIR}/${PYTHON_PN}"

SRC_URI += "file://nova-all \
            file://nova-consoleauth \
            file://nova-novncproxy \
            file://nova.conf \
            file://openrc \
           "

inherit hosts update-rc.d

PACKAGES = "${PN} ${PN}-dbg ${SRCNAME}-controller-misc ${SRCNAME}-controller"
PACKAGES += " ${SRCNAME}-consoleauth"
PACKAGES += " ${SRCNAME}-novncproxy"

do_install_append() {
    if ${@base_contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/nova-all ${D}${sysconfdir}/init.d/nova-all
        install -m 0755 ${WORKDIR}/nova-consoleauth ${D}${sysconfdir}/init.d/nova-consoleauth
        install -m 0755 ${WORKDIR}/nova-novncproxy ${D}${sysconfdir}/init.d/nova-novncproxy
    fi
}

pkg_postinst_${SRCNAME}-controller () {
    if [ "x$D" != "x" ]; then
        exit 1
    fi

    # This is to make sure postgres is configured and running
    if ! pidof postmaster > /dev/null; then
       sudo -u postgres initdb -D /etc/postgresql/
       /etc/init.d/postgresql start
       sleep 0.2
       sudo -u postgres psql -c "CREATE ROLE ${DB_USER} WITH SUPERUSER LOGIN PASSWORD '${DB_PASSWORD}'"
    fi

    sudo -u postgres createdb nova
    nova-manage db sync
}


#FILES_${SRCNAME}-controller = "${files_${SRCNAME}-controller}"
#
# If the compute is built, so we package it out of the way

#FILES_${SRCNAME}-controller-misc = "${files_${SRCNAME}-compute} 
#${files_${SRCNAME}-common} ${files_${PYTHON_PN}}"

FILES_${SRCNAME}-controller = " \
	${bindir} \
	${sysconfdir}/${SRCNAME}/* \
	${sysconfdir}/init.d/nova-all \
"

FILES_${SRCNAME}-consoleauth = " \
	${sysconfdir}/init.d/nova-consoleauth \
"
FILES_${SRCNAME}-novncproxy = " \
	${sysconfdir}/init.d/nova-novncproxy \
"

FILES_${SRCNAME}-controller-misc = " \
	${bindir}/nova-compute \
	${sysconfdir}/init.d/nova-compute \
	${bindir}/nova-manage \
	${bindir}/nova-rootwrap \
	${sysconfdir}/sudoers.d \
	${libdir}"

FILES_${PN} = " \
	${libdir}/python*/site-packages"

RDEPENDS_${SRCNAME}-controller = "${PYTHON_PN} ${SRCNAME}-common ${SRCNAME}-consoleauth \
                                  ${SRCNAME}-novncproxy \
                                  postgresql postgresql-client python-psycopg2"

RCONFLICTS_${SRCNAME}-controller = "${SRCNAME}-compute"

INITSCRIPT_PACKAGES = "${SRCNAME}-controller ${SRCNAME}-consoleauth ${SRCNAME}-novncproxy"
INITSCRIPT_NAME_${SRCNAME}-controller = "nova-all"
INITSCRIPT_NAME_${SRCNAME}-consoleauth = "nova-consoleauth"
INITSCRIPT_NAME_${SRCNAME}-novncproxy = "nova-novncproxy"