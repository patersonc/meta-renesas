# meta-renesas

This branch ports meta-ewaol support on top of the Renesas RZ/G v3.0.0-update2
BSP.

**This work is currently experimental. Do not use in production environments!**

This is a Yocto build layer(version:kirkstone) that provides support for the
RZ/G2 Group of 64bit Arm-based MPUs from Renesas Electronics.

Currently the following boards and MPUs are supported:

- Board: EK874 / MPU: R8A774C0 (RZ/G2E)
- Board: HIHOPE-RZG2M / MPU: R8A774A1 (RZ/G2M v1.3) and R8A774A3 (RZ/G2M v3.0)
- Board: HIHOPE-RZG2N / MPU: R8A774B1 (RZG2N)
- Board: HIHOPE-RZG2H / MPU: R8A774E1 (RZG2H)
- Board: RZG2L SMARC Evaluation Kit / MPU: R9A07G044L (RZ/G2L)
- Board: RZG2LC SMARC Evaluation Kit / MPU: R9A07G044C (RZ/G2LC)
- Board: RZG2UL SMARC Evaluation Kit / MPU: R9A07G043U (RZ/G2UL)
- Board: RZV2L SMARC Evaluation Kit / MPU: R9A07G054L (RZ/V2L)
- Board: RZV2L Development Evaluation Kit / MPU: R9A07G054L (RZ/V2L)

## Patches

To contribute to this layer you should email patches to renesas-rz@renesas.com. Please send .patch files as email attachments, not embedded in the email body.

## Dependencies

This layer depends on:

    URI: https://git.yoctoproject.org/git/poky
    layers: meta, meta-poky, meta-yocto-bsp
    branch: kirkstone
    revision: 453be4d258f71855205f45599eea04589eb4a369

    URI: https://git.openembedded.org/meta-openembedded
    layers: meta-oe, meta-python, meta-multimedia
    branch: kirkstone
    revision: 166ef8dbb14ad98b2094a77fcf352f6c63d5abf2

    URI: https://git.yoctoproject.org/git/meta-gplv2
    layers: meta-gplv2
    branch: kirkstone
    revision: d2f8b5cdb285b72a4ed93450f6703ca27aa42e8a

    URI: https://git.yoctoproject.org/git/meta-virtualization
    layers: meta-virtualization
    branch: kirkstone
    revision: 2fae71cdf0e8c6f398f51219bdf31eac76c662ec

    URI: https://git.yoctoproject.org/git/meta-arm
    layers: meta-arm, meta-arm-bsp, meta-arm-toolchain
    branch: kirkstone
    revision: fc09cc0e8db287600625e64905170a6de24f2686

    URI: https://git.gitlab.arm.com/ewaol/meta-ewaol.git
    layers: meta-ewaol-distro, meta-ewaol-tests
    branch: kirkstone
    revision: 26b2d08f70dcc0e046da8544c968064773c7cc45

## Build Instructions

The *.gitlab-ci.yml* file shows the exact commands used to build the BSP.\
Detailed instrcutions can be found below.

Assume that $WORK is the current working directory.
The following instructions require a Poky installation (or equivalent).

Below git configuration is required:
```bash
    $ git config --global user.email "you@example.com"
    $ git config --global user.name "Your Name"
```

Prepare the build environment as below:
```bash
    $ git clone https://git.yoctoproject.org/git/poky
    $ cd poky
    $ git checkout 453be4d258f71855205f45599eea04589eb4a369
    $ cd ..
    $
    $ git clone https://github.com/openembedded/meta-openembedded
    $ cd meta-openembedded
    $ git checkout 166ef8dbb14ad98b2094a77fcf352f6c63d5abf2
    $ git cherry-pick 3a76ff41a 16f08eb5a
    $ cd ..
    $
    $ git clone https://git.yoctoproject.org/git/meta-gplv2
    $ cd meta-gplv2
    $ git checkout d2f8b5cdb285b72a4ed93450f6703ca27aa42e8a
    $ cd ..
    $
    $ git clone https://github.com/patersonc/meta-renesas.git
    $ cd meta-renesas
    $ git checkout patersonc/kirkstone/BSP-3.0.0-update2/ewaol-dev
    $ cd ..
    $
    $ git clone https://git.yoctoproject.org/git/meta-virtualization
    $ cd meta-virtualization
    $ git checkout 2fae71cdf0e8c6f398f51219bdf31eac76c662ec
    $ cd ..
    $
    $ git clone https://git.yoctoproject.org/git/meta-arm
    $ cd meta-arm
    $ git checkout fc09cc0e8db287600625e64905170a6de24f2686
    $ cd ..
    $
    $ git clone https://git.gitlab.arm.com/ewaol/meta-ewaol.git
    $ cd meta-ewaol
    $ git checkout 26b2d08f70dcc0e046da8544c968064773c7cc45
    $ cd ..
```

Initialize a build using the *oe-init-build-env* script in Poky:
```bash
    $ source poky/oe-init-build-env
```
You will now be inside the *build* directory.

Prepare default configuration files:
```bash
    $ cp $WORK/meta-renesas/docs/template/conf/<machine>/*.conf ./conf/
```
\<machine\>: can be selected from any of the below:
* RZ/G2H:  hihope-rzg2h
* RZ/G2M:  hihope-rzg2m
* RZ/G2N:  hihope-rzg2n
* RZ/G2E:  ek874
* RZ/G2L:  smarc-rzg2l
* RZ/G2LC: smarc-rzg2lc
* RZ/G2UL: smarc-rzg2ul
* RZ/V2L:  smarc-rzv2l, rzv2l-dev

The above configuration templates are for the standard RZ/G BSP.

To add meta-ewaol support we need to add some extra configruation steps.

First, add the layers we need to the *bblayers.conf* file:
```
    $ bitbake-layers add-layer ../meta-ewaol/meta-ewaol-distro
    $ bitbake-layers add-layer ../meta-ewaol/meta-ewaol-tests
    $ bitbake-layers add-layer ../meta-arm/meta-arm-toolchain
    $ bitbake-layers add-layer ../meta-arm/meta-arm
```

Next, add the required configuration to the *local.conf* file:
```
    $ echo "IMAGE_FSTYPES:append=\" wic.gz\"" >> conf/local.conf
    $ echo "WKS_FILE=\"sdimage-rootfs.wks\"" >> conf/local.conf
    $ echo "KERNEL_ALT_IMAGETYPE:append=\" Image.gz\"" >> conf/local.conf
    $ echo "IMAGE_INSTALL:append=\" kernel-image kernel-devicetree htop\"" >> conf/local.conf
    $ echo "DISTRO=\"ewaol\"" >> conf/local.conf
    $ echo "DISTRO_FEATURES:append=\" ewaol-baremetal\"" >> conf/local.conf
    $ echo "INCOMPATIBLE_LICENSE:remove=\"GPL-3.0-only GPL-3.0-or-later\"" >> conf/local.conf
    $ echo "MACHINE_FEATURES:append=\" docker\"" >> conf/local.conf
    $ echo "CONTAINERS_CONFIG_FILE_MD5=\"066dad66874710b95ca625e163252f0a\"" >> conf/local.conf
    $ echo "K3S_CONFIG_FILE_MD5=\"f0925de782533b9cf6969980c257f56a\"" >> conf/local.conf
    $ echo "PACKAGECONFIG:remove:pn-qemu-system-native=\"gtk+ sdl\"" >> conf/local.conf
    $ echo "EWAOL_ROOTFS_EXTRA_SPACE=\"2000000\"" >> conf/local.conf
    $ echo "EWAOL_GENERIC_ARM64_FILESYSTEM=\"0\"" >> conf/local.conf
    $ echo "DISTRO_FEATURES:append=\" ewaol-test\"" >> conf/local.conf
```

If you want to include the EWAOL SDK in the build, also run:
```
    $ echo "DISTRO_FEATURES:append=\" ewaol-sdk\"" >> conf/local.conf
```

Build the target file system image using bitbake:
```bash
    $ bitbake <image>
```
\<image\>: can be selected from any of the below:
* core-image-minimal: Minimal Renesas RZ/G BSP
* ewaol-baremetal-image: EWAOL baremetal distribution image
* ewaol-baremetal-sdk-image: EWAOL baremetal SDK distribution image

After completing the images for the target machine will be available in the
output directory *tmp/deploy/images/\<machine\>*.

Images generated:
* Image (generic Linux Kernel binary image file)
* Image.gz (compressed Linux Kernel binary image file)
* DTB for target machine
* \<image\>-\<machine\>.tar.gz (rootfs tar+gz)
* \<image\>-\<machine\>.ext4 (rootfs ext4 format)
* \<image\>-\<machine\>.wic.gz (rootfs in wic format)
Note: For the rootfs packages the kernel and dtb files will be included in the
*/boot* directory.

## Build Instructions for SDK

Use bitbake -c populate_sdk for generating the toolchain SDK:
For 64-bit target SDK (aarch64):
```bash
    $ bitbake core-image-weston -c populate_sdk
```
The SDK can be found in the output directory _'tmp/deploy/sdk'_

    poky-glibc-x86_64-<image>-aarch64-toolchain-x.x.sh

Usage of toolchain SDK: Install the SDK to the default: _/opt/poky/x.x_
For 64-bit target SDK:
```bash
    $ sh poky-glibc-x86_64-<image>-aarch64-toolchain-x.x.sh
```
For 64-bit application use environment script in _/opt/poky/x.x_
```bash
    $ source /opt/poky/x.x/environment-setup-aarch64-poky-linux
```

