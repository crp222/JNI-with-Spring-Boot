
import os

working_dir = os.getcwd()

def comp(d : str,libraries : str) :
    os.system(f'g++ -O3 -ffast-math {d}\\native.cpp {libraries} -L"{d}"  -I"C:\Program Files\Java\jdk-19\include" -I"C:\Program Files\Java\jdk-19\include\win32" -I"{d}" -shared -o {d}\\native.dll')


for i in os.listdir(working_dir):
    if not os.path.isdir(working_dir+"\\"+i):
        continue
    libraries = ""
    for l in os.listdir(working_dir+"\\"+i):
        if l.startswith("lib"):
            libraries += "-l"+l[3:-2:1]+" "
    comp(working_dir+"\\"+i,libraries)