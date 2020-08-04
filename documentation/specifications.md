# Project definition
Comppa is a project that will do lossless compression and decompression of files of any type using different algorithms. There will be comparisons between the different algorithms, containing atleast the size of the resulting compressed file, as well as an comparison between the speed of compression and decompression using the different algorithms. If time permits, I will possibly look into compressing/decompressing files using lossy compression algorithms.

## Features
* Compression and decompression of any files utilizing several different algorithms
* Files are prosessed in binary mode utilizing bytes

## Possible features
* Encryption
* Exploring lossy compression algorithms
  * Compare the size and speed of these in relation to the lossless algos

## Algorithms and data structures
* BitArray
  * Huffman needs an bitarray. Will look into the subject and decide what data structure would be suitable to use.
* Priority queue utilizing a binary heap. We will use the priority queue as a min queue.
  * Time complexity of different operations:
  * Getting the "first" (smallest) element: O(1)
  * Inserting an element or removing the "first" element: O(log n)

* Huffman coding
  * Produces an optimal binary prefix code
  * Uses the bitarray and also the priority queue
  * Uses a probability distribution over the source alphabet and maps the the most frequent symbols to the shortest codewords (compressed alphabet)
  * Creates an Huffman tree where the nodes are the symbols with their optimal prefix codes
  * Total time complexity of the Huffman code utilizing the priority queue is O(n log m), where m is the length of the input in bytes and n is the length of the input alphabet

* More details including algorithms will be added later

## Sources
* Juha Kärkkäinen, "DATA COMPRESSION TECHNIQUES" HY coursematerial. [Fall 2017]. https://courses.helsinki.fi/fi/csm12103/119285062
* A. Laaksonen, "Tietorakenteet ja algoritmit". [13. marraskuuta 2018]
* n.d., "Lossless compression", _Wikipedia_, fetched [24.07.2020]. https://en.wikipedia.org/wiki/Lossless_compression
* n.d., "Data compression", _Wikipedia_, fetched [24.07.2020]. https://en.wikipedia.org/wiki/Data_compression
* n.d., "Lossy compression", _Wikipedia_, fetched [24.07.2020]. https://en.wikipedia.org/wiki/Lossy_compression

## Other interesting algorithms that might be implemented
* Shannon-Fano, simpler than huffman and usually produces an (nearly) optimal prefix code
* Package-merge, length-limited Huffman code. Produces an optimal prefix code
* Hu-Tucker, constructs an optimal aplhabetic prefix code
