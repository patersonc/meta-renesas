# meta-renesas

**This work is currently experimental. Do not use in production environments!**

This is a Yocto build layer(version:kirkstone) that provides support for the
RZ/G2 Group of 64bit Arm-based MPUs from Renesas Electronics.

Currently the following boards and MPUs are supported:

- Board: EK874 / MPU: R8A774C0 (RZ/G2E)
- Board: HIHOPE-RZG2M / MPU: R8A774A1 (RZ/G2M v1.3) and R8A774A3 (RZ/G2M v3.0)
- Board: HIHOPE-RZG2N / MPU: R8A774B1 (RZG2N)
- Board: HIHOPE-RZG2H / MPU: R8A774E1 (RZG2H)
- Board: RZG2L SMARC Evaluation Kit / MPU: R9A77G044L (RZ/G2L)
- Board: RZG2LC SMARC Evaluation Kit / MPU: R9A77G044C (RZ/G2LC)
- Board: RZG2UL SMARC Evaluation Kit / MPU: R9A77G043U (RZ/G2UL)

## Patches

To contribute to this layer you should email patches to renesas-rz@renesas.com.
Please send .patch files as email attachments, not embedded in the email body.

## Dependencies

This layer depends on:

    URI: git://git.yoctoproject.org/poky
    layers: meta, meta-poky, meta-yocto-bsp
    branch: kirkstone
    revision: 453be4d258f71855205f45599eea04589eb4a369

    URI: git://git.openembedded.org/meta-openembedded
    layers: meta-oe, meta-python, meta-multimedia
    branch: kirkstone
    revision: 166ef8dbb14ad98b2094a77fcf352f6c63d5abf2

    URI: http://git.yoctoproject.org/cgit.cgi/meta-gplv2/
    layers: meta-gplv2
    branch: kirkstone
    revision: d2f8b5cdb285b72a4ed93450f6703ca27aa42e8a

## Build Instructions

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
    $ cd ..
    $
    $ git clone https://git.yoctoproject.org/git/meta-gplv2
    $ cd meta-gplv2
    $ git checkout d2f8b5cdb285b72a4ed93450f6703ca27aa42e8a
    $
    $ git clone  https://github.com/renesas-rz/meta-renesas.git
    $ cd meta-renesas
    $ git checkout kirkstone/BSP-3.0.0
    $ cd ..
```

Initialize a build using the 'oe-init-build-env' script in Poky:
```bash
    $ source poky/oe-init-build-env
```

Prepare default configuration files:
```bash
    $ cp $WORK/meta-renesas/docs/template/conf/<machine>/*.conf ./conf/
```
\<machine\>: hihope-rzg2h, hihope-rzg2m, hihope-rzg2n, ek874, smarc-rzg2l,
smarc-rzg2lc, smarc-rzg2ul

Build the target file system image using bitbake:
```bash
    $ bitbake <image>
```
\<image\>: core-image-minimal

After completing the images for the target machine will be available in the
output directory _'tmp/deploy/images/\<supported board name\>'_.

Images generated:
* Image (generic Linux Kernel binary image file)
* DTB for target machine
* \<image\>-\<machine\>.tar.bz2 (rootfs tar+bzip2)
* \<image\>-\<machine\>.ext4  (rootfs ext4 format)

## Build Instructions for SDK

Use bitbake -c populate_sdk for generating the toolchain SDK:
For 64-bit target SDK (aarch64):
```bash
    $ bitbake core-image-weston -c populate_sdk
```
The SDK can be found in the output directory _'tmp/deploy/sdk'_

    poky-glibc-x86_64-core-image-weston-aarch64-toolchain-x.x.sh

Usage of toolchain SDK: Install the SDK to the default: _/opt/poky/x.x_
For 64-bit target SDK:
```bash
    $ sh poky-glibc-x86_64-core-image-weston-aarch64-toolchain-x.x.sh
```
For 64-bit application use environment script in _/opt/poky/x.x_
```bash
    $ source /opt/poky/x.x/environment-setup-aarch64-poky-linux
```

## Build configs

It is possible to change some build configs as below:
### GPLv3
Choose to not allow, or allow, GPLv3 packages

By default GPLv3 licenses are not allowed. All recipes that have a GPLv3 license
will be downgraded to older versions that have an alternative license if
possible (provided by meta-gplv2).

Users can enable the use of GPLv3 packages by removing the below line from the
local.conf file:
```
- INCOMPATIBLE_LICENSE = "GPL-3.0-only GPL-3.0-or-later"
```
