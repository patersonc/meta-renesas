FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " \
    file://0001-clk-renesas-rzg2l-Fix-reset-status-function.patch \
"
