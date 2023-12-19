
import os

working_dir = os.getcwd()

def comp(d : str) :
    os.system(f'c++ -O3 -ffast-math -I"C:\Program Files\Java\jdk-19\include" -I"C:\Program Files\Java\jdk-19\include\win32" -shared -o {d}\\native.dll {d}\\native.cpp')


for i in os.listdir(working_dir):
    if not os.path.isdir(working_dir+"\\"+i):
        continue
    comp(working_dir+"\\"+i)