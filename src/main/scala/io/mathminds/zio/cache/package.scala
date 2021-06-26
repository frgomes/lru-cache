// Copyright 2021 Mathminds and contributors
// More information: https://mathminds.io
//  
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//  
//         http://www.apache.org/licenses/LICENSE-2.0
//  
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.mathminds.zio

import zio._

package object cache {

  /** Common trait for all implementations involving LRU caches. */
  trait LRUCacheLike[K, V] extends StorageLike[K, V] {
    /** Commit modifications on the underlying cache */
    //FIXME: Should employ ZIO instead, in order to catch failures on the secondary storage.
    def commit: UIO[(Map[K, CacheItem[K, V]], Option[K], Option[K])]
  }
   
   
  /** Common trait for all implementations involving secondary storage. */
  trait StorageLike[K, V] {
    /** Returns a value `V` idenfified by key `K`, if that such key exists. */
    //FIXME: For performance reasons, should not enforce java.lang.Exception hierarchy.
    def get(key: K): IO[NoSuchElementException, V] 
   
    /** Stores a value `V` identified by key `K`. */
    //FIXME: Should employ ZIO instead, in order to catch failures on the secondary storage.
    def put(key: K, value: V): UIO[Unit] // should employ ZIO
  }
}
