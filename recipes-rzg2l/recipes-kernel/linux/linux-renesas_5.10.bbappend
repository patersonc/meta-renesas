FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://0001-Fixed-an-issue-that-caused-flicker-when-outputting-t.patch \
"
