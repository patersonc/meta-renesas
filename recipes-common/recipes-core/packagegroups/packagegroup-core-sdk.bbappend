# Remove these packages if user chooses to build without GPLv3
# since they do not have other license
RDEPENDS:packagegroup-core-sdk:remove = "${@bb.utils.contains('INCOMPATIBLE_LICENSE', 'GPL-3.0-only', 'ccache', '', d)}"
