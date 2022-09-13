FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
        file://0001-Fix-annoying-netpol-log.patch;patchdir=src/import \
"
