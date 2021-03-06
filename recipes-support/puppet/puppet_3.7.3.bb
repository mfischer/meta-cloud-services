SUMMARY = "Open source Puppet is a configuration management system"
HOMEPAGE = "https://puppetlabs.com/puppet/puppet-open-source"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f257790c70561550fd666c6e0044fd89"

SRC_URI = " \
    https://downloads.puppetlabs.com/puppet/puppet-${PV}.tar.gz \
    file://add_puppet_gemspec.patch \
    file://puppet.conf \
    file://maint-Change-spec-test-to-pass-on-ruby-2.2.patch \
    file://MAINT-Fix-duplicate-key-which-ruby-2.2-complains-abo.patch \
    file://safe_yaml-syck-isn-t-available-for-Ruby-2.0.0.patch \
"
SRC_URI[md5sum] = "cc294da1d51df07bcc7f6cf78bd90ce0"
SRC_URI[sha256sum] = "4a3bd7ddb51072c3dd898a8de158cde204a2d8fd0b84e8ac806b84c074348637"

inherit ruby

DEPENDS += " \
        ruby \
        facter \
        hiera \
"

RDEPENDS_${PN} += " \
        ruby \
        facter \
        hiera \
        ruby-shadow \
        bash \
"

RUBY_INSTALL_GEMS = "puppet-${PV}.gem"

do_install_append() {
    install -d ${D}${sysconfdir}/puppet
    install -d ${D}${sysconfdir}/puppet/manifests
    install -d ${D}${sysconfdir}/puppet/modules

    install -m 655 ${S}/conf/auth.conf ${D}${sysconfdir}/puppet/
    install -m 655 ${S}/conf/fileserver.conf ${D}${sysconfdir}/puppet/
    install -m 655 ${S}/conf/tagmail.conf ${D}${sysconfdir}/puppet/
    install -m 655 ${WORKDIR}/puppet.conf ${D}${sysconfdir}/puppet/
}
