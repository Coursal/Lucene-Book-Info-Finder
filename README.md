# Lucene-Book-Info-Finder
A Lucene 8.0.0 API based java application that given a `.txt` file of a book, returns the book's title and date along awith their offsets.

1) An analyzer splits apart the tokens inside the file
2) The first few tokens (up to 1000 characters in) are searched for labels such as `Title:` and `Date:`
3) Everything right after the labels, including their offsets, are being returned to the screen


### Execution
**`java book_info_finder.BookInfoFinder <.txt file path>`**


### Examples
#### Moby Dick
![](http://i68.tinypic.com/8y94wn.png)
#### Metamorphosis
![](http://i67.tinypic.com/do5na0.png)
#### The Odyssey
![](http://i65.tinypic.com/2w5j5o3.png)


`.txt` book files taken from [Project Gutenberg](http://www.gutenberg.org/).

Essential Lucene 8.0.0 API `.jar` files included.
