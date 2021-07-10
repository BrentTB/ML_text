import pathlib

working_directory = str(pathlib.Path(__file__).parent.absolute()) +"/"

filename = "soccer" # soccer, redditFile

read = open(working_directory+filename+".txt", "r")

sett = {''}
for line in read:

    if (len(line.replace("\n", "").strip())>5): # 10302
        sett.add(line)

read.close()


write = open(working_directory+filename+".txt", "w")

for line in sett:
    write.write(line)

write.close()