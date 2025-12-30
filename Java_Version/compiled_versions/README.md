# Compiled version #
This folder contains the different standalone Java "compiled" versions.
Download the version you like the most and unzip it or run it.

The file ramtestdec.java will be always the last version. In the ZIP file you may find the last version and other previous.

**Requiriments**
1. Java JRE/JDK 8 or later.
3. The codes obtained by any test (Minerva ROM, SBasic implementation, whatever)
4. It runs (tested) with MSWindows, Linux, MacMini M4 (ARM), but should work fine in any OS with Java.

**syntax**

java -jar nameofthejar.jar [OPTION] [WRITE,READ,ADDRESS] [-NC]

**Example**

java -jar ramtestdec.jar 67084842 67084842 0003CC88


## Changelog ##

0.93 => compiled to run from Java 11 + aesthetic changes

0.94 => compiled to run from Java 1.8 + aesthetic changes + factoring

0.96b => added graphical representation of the faulty ram map zone afected also added no colors option for terminals that doesn't support ANSI codes. Other minor changes.

0.97c => Added graphical layout of the results. Added some extra information, bug fixes and minor changes.

0.98a => Included rare case when Write == Read data. Also representation improvements, small bugs fixes related with the text and start to document the code properly.