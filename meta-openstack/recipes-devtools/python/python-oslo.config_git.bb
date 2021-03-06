DESCRIPTION = "API supporting parsing command line arguments and .ini style configuration files."
HOMEPAGE = "https://pypi.python.org/pypi/oslo.config/2.6.0"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c46f31914956e4579f9b488e71415ac8"

PV = "3.17.0+git${SRCPV}"
SRCREV = "2f2c1839b7423185a6a48e7b3ca3c3274d5ba8f3"

SRCNAME = "oslo.config"
SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/newton"

S = "${WORKDIR}/git"

inherit setuptools rmargparse

DEPENDS += " \
        python-pbr \
        python-pip \
        "

# Satisfy setup.py 'setup_requires'
DEPENDS += " \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
    python-pbr \
    python-netaddr \
    python-six \
    python-stevedore \
    python-debtcollector \
    python-oslo.i18n \
    python-rfc3986 \
    "
