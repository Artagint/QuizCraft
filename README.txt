Group: Harper Hakes, Artem Tagintsev
Class: CS458 - Mobile Application Development
Professor: Paul Bonamy
Date: 04/30/2025

SOFTWARE REQUIREMENTS:
-Have the latest version of Android Studios


USING THE APP:
1) To start the app up, click on "Run 'App'" in the top-right part of the screen, wait for app to load

2) Once the app loads you will be met with the Login screen, since you are reading this and most likely
    a new user, in the top-right you will see a Sign-Up button, click on that to go to the Sign-Up page
    where you can make an account

3) Fill out the necessary information properly and click the "Sign-Up" button, this will save your
    email/password to the firebase database and also log you in. From here to test the login you can
    click on the profile icon in the top-right and that will give you a dropdown, click on "Sign Out"
    then simply log back in with your email/password. If you forgot your password simply click on
    "Forgot Password" and firebase will send you an email prompting for a password change, that is how
    you login.

4) On the main screen you will see 3 buttons; Go to Classes, Make a Class, and a Profile icon. The
    settings under the profile icon do not work (weren't implemented), but the sign out works.

5) The workflow here is let's say you want to create a new class, so you click on 'Make a Class', this
    takes you to that page where you are asked for a class name and to upload relevant file(s) for that
    class. No class name duplicates are allowed, the app will notify you if something is wrong. To upload
    files from Android Studios, just go to downloads after clicking on Upload File(s) and drag and drop some
    file you have into there, then click on that file to select it. You can upload 1 file at a time. Upon
    clicking Done, the file will be added to the firebase database and will be under the Class Name in the
    database. One class can have many files.

6) After that go back to the main screen

7) Then click on Go to Classes, here the classes you've made will be shown in a list, upon clicking on
    any of the classes it'll give you an opportunity to upload more file(s) (not implemented) or go to
    the quiz page for that class by clicking on 'Take Quiz'. Unfortunately we didn't have time to
    implement the quiz feature.


EXTRA INFO:
-Unfortunately we didn't have time to implement the quiz feature, what we got done was UI for the app,
    user signup/ login, changing passwords, sign out, a lot of error checks for every field (like strong
    passwords, valid email addresses, and so on). And we also did file upload for each class that is stored
    in a firebase database, and also listing the classes the user has.