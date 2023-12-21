import os

working_dir = os.getcwd()

def get_func_names(file : str):
    func_names = []
    still_line = False
    with open(file) as f:
        for line in f:
            if still_line:
                func_names[-1] += line
                still_line = False
            if line.startswith("JNIEXPORT"):
                func_names.append(line)
                still_line = True
    return func_names

def make_cpp_file(func_names : list[str],d : str,header : str):
    content = f'#include "{header}"\n\n'
    content += "using namespace std;\n\n"
    for func in func_names:
        content += func[:len(func)-2:] + " {\n\n}\n\n"
    with open(d+"\\native.cpp","w") as f:
        f.write(content)

def make_dir(d : str):
    last_dir_name = d.split('\\')[-1]
    if not os.path.exists(d+f"\\{last_dir_name}.java"):
        return
    header_name = f"com_jni_{last_dir_name}_{last_dir_name}.h"
    jfile = d+f"\\{last_dir_name}.java"
    os.system(f"javac -h {d} {jfile}")
    func_names = get_func_names(d+"\\"+header_name)
    if not os.path.exists(d+"\\native.cpp"):
        make_cpp_file(func_names,d,header_name)


for i in os.listdir(working_dir):
    if not os.path.isdir(working_dir+"\\"+i):
        continue
    make_dir(working_dir+"\\"+i)