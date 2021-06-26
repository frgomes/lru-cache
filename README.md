# lru-cache

A multithreaded, lock-free, efficient, in-memory LRU cache with support for secondary storage.

## Motivation

There are several LRU cache implementations around and chances are that you've already used one before.

This implementation provides an in-memory LRU cache optionally backed by a secondary storage layer.
The in-memory LRU cache is designed to be relatively small, relying onto the secondary storage layer when cache misses occur or when items are about to be kicked off from memory. In both cases, the secondary storage layer comes in for fulfilling the requirements of the in-memory cache.

## Caveats

At the time of this writing, the secondary storage layer is not yet supported.

## Credits

 1. A great deal of ideas and code was shamelessly borrowed from [this article from Jorge Vasquez](https://scalac.io/blog/how-to-write-a-completely-lock-free-concurrent-lru-cache-with-zio-stm/) and his [public code repository](https://github.com/jorge-vasquez-2301/zio-lru-cache).

 2. [ZIO](https://zio.dev/) is a functional library which focuses on solving business problems, keeping developers away from complexities commonly observed on functional libraries. With ZIO you write [functional code which can be understood by mere mortals](https://leanpub.com/fpmortals), allowing you to be more effective transforming business problems into profits.
