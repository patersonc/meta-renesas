#
# base recipe: meta/recipes-devtools/binutils/binutils_2.30.bb
# base branch: master
# base commit: 53dfa673d78216f852a47bdb48392ee213e3e1cd
#

require binutils.inc

DEPENDS += "flex bison zlib"

EXTRA_OECONF += "--with-sysroot=/ \
                --enable-install-libbfd \
                --enable-install-libiberty \
                --enable-shared \
                --with-system-zlib \
                "
EXTRA_OECONF:class-native = "--enable-targets=all \
                             --enable-64-bit-bfd \
                             --enable-install-libiberty \
                             --enable-install-libbfd \
                             --disable-werror"

do_install:class-native () {
	autotools_do_install

	# Install the libiberty header
	install -d ${D}${includedir}
	install -m 644 ${S}/include/ansidecl.h ${D}${includedir}
	install -m 644 ${S}/include/libiberty.h ${D}${includedir}

	# We only want libiberty, libbfd and libopcodes
	rm -rf ${D}${bindir}
	rm -rf ${D}${prefix}/${TARGET_SYS}
	rm -rf ${D}${prefix}/lib/ldscripts
	rm -rf ${D}${prefix}/share/info
	rm -rf ${D}${prefix}/share/locale
	rm -rf ${D}${prefix}/share/man
	rmdir ${D}${prefix}/share || :
	rmdir ${D}/${libdir}/gcc-lib || :
	rmdir ${D}/${libdir}64/gcc-lib || :
	rmdir ${D}/${libdir} || :
	rmdir ${D}/${libdir}64 || :
}

# Split out libbfd-*.so so including perf doesn't include extra stuff
PACKAGE_BEFORE_PN += "libbfd"
FILES:libbfd = "${libdir}/libbfd-*.so"

BBCLASSEXTEND = "native nativesdk"
