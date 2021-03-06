DESCRIPTION = "Oslo utils"
HOMEPAGE = "https://launchpad.net/oslo"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

SRCNAME = "oslo.utils"
SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/newton"

PV = "3.16.0+git${SRCPV}"
SRCREV = "0aae41e0c1d3d965181b22b6fd25fb1bc0442a35"
S = "${WORKDIR}/git"

inherit setuptools

# DEPENDS_default: python-pip

DEPENDS += " \
        python-pip \
        python-pbr \
        "

# Satisfy setup.py 'setup_requires'
DEPENDS += " \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-pbr \
        python-six \
        python-iso8601 \
        python-oslo.i18n \
        python-monotonic \
        python-pytz \
        python-netaddr \
        python-netifaces \
        python-debtcollector \
        python-funcsigs \
        python-pyparsing \
        "
