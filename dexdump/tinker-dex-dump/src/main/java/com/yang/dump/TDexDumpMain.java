package com.yang.dump;

import com.yang.dump.struct.DexPatchFile;
import com.yang.dump.util.TypedValue;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class TDexDumpMain {
    private static final String ARG_HELP = "--help";
    private static final String ARG_HELP_ABBR = "-h";
    private static final String ARG_DEX = "--dex";
    private static final String ARG_HEADER = "--header";
    private static final String ARG_SECTION = "--section";
    public static final int ERRNO_USAGE = -1;

    public static void main(String[] args) {
        TDexDumpMain m = new TDexDumpMain();
        m.run(args);
    }

    private void run(String[] args) {
        if (args.length < 1) {
            goToError();
        }

        ReadArgs readArgs = new ReadArgs(args).invoke();
        File dexFile = readArgs.getTinkerDexFile();
        DexPatchFile dexPatchFile = parseDex(dexFile);

        printInfo(dexPatchFile,
                readArgs.isShowAll(),
                readArgs.isShowHeader(),
                readArgs.getSectionName());
    }

    private DexPatchFile parseDex(File inputFile) {
        DexPatchFile dexPatchFile = null;
        try {
            dexPatchFile = new DexPatchFile(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("parseDex error");
            System.exit(ERRNO_USAGE);
        }

        return dexPatchFile;
    }

    private void printInfo(DexPatchFile dexPatchFile,
                           boolean isShowAll,
                           boolean isShowHeader,
                           String sectionName) {
        if (isShowAll) {
            dexPatchFile.printHeader();
            dexPatchFile.printSection(DexPatchFile.SectionType.StringDataSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.TypeIdSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.ProtoIdSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.FieldIdSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.MethodIdSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.ClassDefSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.TypeListSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSetRefListSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSetSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.ClassDataSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.CodeSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.DebugInfoSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.StaticValueSection);
            dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationsDirectorySection);
            return;
        }

        if (isShowHeader) {
            dexPatchFile.printHeader();
        }

        if (sectionName != null) {
            if (sectionName.equalsIgnoreCase("StringData")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.StringDataSection);
            } else if (sectionName.equalsIgnoreCase("TypeId")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.TypeIdSection);
            } else if (sectionName.equalsIgnoreCase("ProtoId")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.ProtoIdSection);
            } else if (sectionName.equalsIgnoreCase("FieldId")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.FieldIdSection);
            } else if (sectionName.equalsIgnoreCase("MethodId")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.MethodIdSection);
            } else if (sectionName.equalsIgnoreCase("ClassDef")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.ClassDefSection);
            } else if (sectionName.equalsIgnoreCase("TypeList")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.TypeListSection);
            } else if (sectionName.equalsIgnoreCase("AnnotationSetRefList")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSetRefListSection);
            } else if (sectionName.equalsIgnoreCase("AnnotationSet")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSetSection);
            } else if (sectionName.equalsIgnoreCase("ClassData")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.ClassDataSection);
            } else if (sectionName.equalsIgnoreCase("Code")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.CodeSection);
            } else if (sectionName.equalsIgnoreCase("DebugInfo")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.DebugInfoSection);
            } else if (sectionName.equalsIgnoreCase("Annotation")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationSection);
            } else if (sectionName.equalsIgnoreCase("StaticValue")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.StaticValueSection);
            } else if (sectionName.equalsIgnoreCase("AnnotationsDirectory")) {
                dexPatchFile.printSection(DexPatchFile.SectionType.AnnotationsDirectorySection);
            }
        }
    }

    private void printUsage(PrintStream out) {
        String command = "tinker-dex-dump.jar";
        out.println();
        out.println();
        out.println("用法:java -jar " + command + " --dex *.dex [--header] [--section section-name]");
        out.println();
        out.println("  --header\t\t显示头部");
        out.println("  --section\t\t显示section区域");
        out.println();
        out.println("section选项(不区分大小写):");
        out.println("  StringData");
        out.println("  TypeId");
        out.println("  ProtoId");
        out.println("  FieldId");
        out.println("  MethodId");
        out.println("  ClassDef");
        out.println("  TypeList");
        out.println("  AnnotationSetRefList");
        out.println("  AnnotationSet");
        out.println("  ClassData");
        out.println("  Code");
        out.println("  DebugInfo");
        out.println("  Annotation");
        out.println("  StaticValue");
        out.println("  AnnotationsDirectory");
        out.println();
    }

    public void goToError() {
        printUsage(System.err);
        System.exit(ERRNO_USAGE);
    }

    private class ReadArgs {
        private String[] args;
        private File tinkerDexFile;
        private boolean isShowHeader;
        private boolean isShowAll;
        private String sectionName;

        private String[] sections = {
            "StringData",
            "TypeId",
            "ProtoId",
            "FieldId",
            "MethodId",
            "ClassDef",
            "TypeList",
            "AnnotationSetRefList",
            "AnnotationSet",
            "ClassData",
            "Code",
            "DebugInfo",
            "Annotation",
            "StaticValue",
            "AnnotationsDirectory"
        };

        public ReadArgs(String[] args) {
            this.args = args;
        }

        public File getTinkerDexFile() {
            return tinkerDexFile;
        }

        public boolean isShowHeader() {
            return isShowHeader;
        }

        public boolean isShowAll() {
            return isShowAll;
        }

        public String getSectionName() {
            return sectionName;
        }

        public ReadArgs invoke() {
            for (int index = 0; index < args.length; index++) {
                String arg = args[index];
                if (arg.equals(ARG_HELP) || arg.equals(ARG_HELP_ABBR)) {
                    goToError();
                } else if (arg.equals(ARG_DEX)) {
                    if (index == args.length - 1 || args[index + 1].endsWith(TypedValue.FILE_DEX)) {
                        System.err.println("Missing dex file argument");
                        goToError();
                    }

                    tinkerDexFile = new File(args[++index]);

                    if (!tinkerDexFile.exists()) {
                        System.err.println(tinkerDexFile.getAbsolutePath() + " does not exist");
                        goToError();
                    }
//                    System.out.println("special dex file path:" + tinkerDexFile.getAbsolutePath());
                } else if (arg.equals(ARG_HEADER)) {
                    isShowHeader = true;
                } else if (arg.equals(ARG_SECTION)) {
                    if (index == args.length - 1) {
                        System.err.println("Missing section name argument");
                        goToError();
                    }

                    if (!matchSection(args[index + 1])) {
                        System.err.println("section name argument is invalid");
                        goToError();
                    }

                    sectionName = args[++index];
                } else {
                    System.err.println("argument[" + arg + "] is invalid");
                    goToError();
                }
            }

            if (!isShowHeader && sectionName == null) {
                isShowAll = true;
            }

            return this;
        }

        private boolean matchSection(String section) {
            for(int i = 0; i < sections.length; i++) {
                if (section.equalsIgnoreCase(sections[i])) {
                    return true;
                }
            }
            return false;
        }
    }
}
