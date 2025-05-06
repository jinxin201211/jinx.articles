temp = "D:\Program Files\scrcpy-win64-v1.23\scrcpy.exe"
strCommand = "cmd /c " & Chr(34) & temp & Chr(34) & " --turn-screen-off --stay-awake --show-touches --power-off-on-close"

For Each Arg In WScript.Arguments
    strCommand = strCommand & " """ & replace(Arg, """", """""""""") & """"
Next

CreateObject("Wscript.Shell").Run strCommand, 0, false
