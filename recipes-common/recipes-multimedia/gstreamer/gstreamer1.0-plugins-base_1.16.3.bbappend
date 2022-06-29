FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://gstpbfilter.conf \
	file://0001-playback-Add-source-for-getting-video-filter-from-fi.patch \
"


do_install:append() {
    install -Dm 644 ${WORKDIR}/gstpbfilter.conf ${D}/etc/gstpbfilter.conf
}

FILES:${PN}:append = " \
    ${sysconfdir}/gstpbfilter.conf \
"
