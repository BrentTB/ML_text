import praw
import os
import pathlib

# from praw import m MoreComments



# posts = ["n6tk7z", "n62yaf","n6dcfb"]
posts = ["knlv6o", "gy5jy9","gqg0tg","gtbu4g","giwv31"]

working_directory = str(pathlib.Path(__file__).parent.absolute()) +"/"

# print(working_directory)
# print(pathlib.Path(__file__).parent.absolute())

# f = open(working_directory+"redditFile.txt", "a") # w to overwrite any data 
f = open(working_directory+"soccer.txt", "a")



count=0
for ind, post in enumerate(posts):

    print(f"\npost: {ind+1}")

    submission = r.submission(id= post)

    submission.comments.replace_more(limit= None ) # = None

    print("*starting*")

    for ind2, comment in enumerate(submission.comments.list()):


        if((ind2+1)%100==0):
            print(f"comment: {ind2+1}")

        inp = str(comment.body)


        while ("http" in inp):
            indS = inp.index("http")
            indE = inp.find(" ", indS)
            inp = inp[:indS] + inp[indE:]

        while ("/r/" in inp):
            indS = inp.index("/r/")
            indE = inp.find(" ", indS)
            inp = inp[:indS] + inp[indE:]
        while ("r/" in inp):
            indS = inp.index("r/")
            indE = inp.find(" ", indS)
            inp = inp[:indS] + inp[indE:]
        while ("R/" in inp):
            indS = inp.index("R/")
            indE = inp.find(" ", indS)
            inp = inp[:indS] + inp[indE:]

        while ("u/" in inp):
            indS = inp.index("u/")
            indE = inp.find(" ", indS)
            inp = inp[:indS] + inp[indE:]


        inp = inp.replace("[removed]","")
        inp = inp.replace(">!","")
        inp = inp.replace("!<","")
        inp = inp.replace("[","")
        inp = inp.replace("]","")
        inp = inp.replace("(","")
        inp = inp.replace(")","")
        inp = inp.replace("  "," ")
        inp = inp.replace("\n","")
        inp = inp.strip()

        if (not inp):
            continue


        inpTmp = ""

        for digit in inp:
            if (ord(digit) >=32 and ord(digit) <=126):
                inpTmp += digit

        inp = inpTmp


        f.write(inp + "\n")
        count+= len(comment.body)

f.close()
print(count)


