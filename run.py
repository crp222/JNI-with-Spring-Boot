
import os
import threading

working_dir = os.getcwd()

def run_view():
    os.system(f"npm run dev --prefix {working_dir}\\simview")

def run_api():
    os.chdir(f"{working_dir}\\simapi")
    os.system("mvnw spring-boot:run")

def compile_cpp():
    os.chdir(f"{working_dir}\\simapi\\src\\main\\java\\com\\jni")
    os.system("python compile.py")

compile_cpp()

view_thread = threading.Thread(target=run_view)
api_thread = threading.Thread(target=run_api)

view_thread.start()
api_thread.start()