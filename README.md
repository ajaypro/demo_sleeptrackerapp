TrackMySleepQuality - Adding Header to RV
==================================

![Screenshot](https://github.com/ajaypro/demo_sleeptrackerapp/blob/add-header-RV/header_RV.png)


Changes Made
-------------
* RV which already has VH to display sleepnight items using listAdapter, we are going to add header to RV
* Two ways can be done either adding header as list item which we don't have to make much changes to adapter class, we only have to 
  display item 0 as header and remaining items as data items
* Second way: We create a separte header as itemType and create a VH for it, in the adapter class we will override `getItemType()` 
   to find out itemType and call the corresponding VH to be binded in the `onBindViewHolder()`

Implementation
-------------

* We create a sealed class `DataItem` which encloses the DataItemType which is header and sleepnight(Dataset) and an abstract `id` variable.
* create a separate layout for header and inflate it while creatingVH
  using which diffUtil will identify the id of which DataItem
* Override `getItemType()` to check for the itemType and call corresponding DataItem
* `onCreateViewHolder` -> check viewType and call correponding VH
* `onBindViewHolder` -> you have data only on sleepnight so check if its sleepViewHolder and bind its dataItem
* `addAndSubmitList()` -> check for list if its null return header else attach header+ dataItemList, this will be passed as argument
   to `submitList(list)` which will be called by adapter to get data in the fragment.
   
Catch
-----
* Manipulating list in `addAndSubmitList()` in main thread is fine for small list size when size increases we need to handle this in
  bg thread so we use coroutine and manipulate list items in default context and switch to main thread when submitting the list 
  ```
  fun addAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
  ```

License
-------

Copyright 2019 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
