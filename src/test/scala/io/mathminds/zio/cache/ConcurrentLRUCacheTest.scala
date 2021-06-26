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

package io.mathminds.zio.cache

import zio.test._

object ConcurrentLRUCacheTest extends DefaultRunnableSpec {
  import zio._
  import zio.console._
  import zio.test.Assertion._
  import zio.test.environment.TestConsole
  import java.io.IOException

  def spec = suite("ConcurrentLRUCache")(
    testM("can't be created with non-positive capacity") {
      assertM(ConcurrentLRUCache.make[Int, Int](0).run)(
        fails(hasMessage(equalTo("Capacity must be a positive number!")))
      )
    },
    testM("works as expected") {
      val expectedOutput = Vector(
        "Putting (1, 1)\n",
        "Putting (2, 2)\n",
        "Getting key: 1\n",
        "Obtained value: 1\n",
        "Putting (3, 3)\n",
        "Getting key: 2\n",
        "Key does not exist: 2\n",
        "Putting (4, 4)\n",
        "Getting key: 1\n",
        "Key does not exist: 1\n",
        "Getting key: 3\n",
        "Obtained value: 3\n",
        "Getting key: 4\n",
        "Obtained value: 4\n"
      )
      for {
        lruCache <- ConcurrentLRUCache.make[Int, Int](2)
        _        <- put(lruCache, 1, 1)
        _        <- put(lruCache, 2, 2)
        _        <- get(lruCache, 1)
        _        <- put(lruCache, 3, 3)
        _        <- get(lruCache, 2)
        _        <- put(lruCache, 4, 4)
        _        <- get(lruCache, 1)
        _        <- get(lruCache, 3)
        _        <- get(lruCache, 4)
        output   <- TestConsole.output
      } yield {
        assert(output)(equalTo(expectedOutput))
      }
    }
  )
 
  private def get[K, V](lruCache: ConcurrentLRUCache[K, V], key: K): ZIO[Console, IOException, Unit] =
    (for {
      _ <- putStrLn(s"Getting key: $key")
      v <- lruCache.get(key)
      _ <- putStrLn(s"Obtained value: $v")
    } yield ()).catchAll(ex => putStrLn(ex.getMessage))
 
  private def put[K, V](lruCache: ConcurrentLRUCache[K, V], key: K, value: V): ZIO[Console, IOException, Unit] =
    putStrLn(s"Putting ($key, $value)") *> lruCache.put(key, value)
}
