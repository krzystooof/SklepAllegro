# Sklep Allegro
Small two screen app made as internship task for Allegro.pl

## Main tasks:
- download and parse JSON ([Gson](https://github.com/google/gson "Gson") used)

- show **selected** and sorted parsed objects on MainScreen:
download image from URL given in object ([Glide](https://github.com/bumptech/glide/ "Glide") used)
show image, title and price (amount and currency)
**selected** -  have `50 =< price.amount <= 1000`

- on click offer show second screen
 second screen  -  title, image, price, and description (HTML format, may be parsed)
## Additional work:

- Main Screen objects swip actions  (left, right)

- Main Screen additional objects (items with other layout and functions in Recycler)

- Second (detailed) Screen made as Recycler (easy scaling and expanding)

- Second (detailed) Screen HTML parsed

- Shared Preferences - save data on moving between screens
 when switching to Main Screen no need to download data every time,  not moving to top of recycler

- Main Screen Recycler's Snackbar

- Logging

- no connection info

![noInternet.gif](media/noInternet.gif=250x)
![swipe.gif](media/swipe.gif=250x)
![switch.gif](media/switch.gif=250x)
