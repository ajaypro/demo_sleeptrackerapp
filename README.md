TrackMySleepQuality - Databinding with RV, @BindingAdapter
==================================

Starter code for Android Kotlin Fundamentals Codelab 6.1 Room

Introduction
------------

TrackMySleepQuality is an app for recording sleep data for each night. 
You can record a start and stop time, assign a quality rating, and clear the database. 

In this codelab, working from this starter app,
you will implement the Room database that holds the sleep data. 
You will then use instrumented tests to verify that this backend works. 


Things to Learn
--------------

Using ListAdapter: 

* We use list adapter which takes Data, VH as type and diffutil as constructor argument.
    Uses diffutil which further is implemented by asyncListDiffer which runs in bg thread to provide 
    difference in list 
  * Also internally implements `getItem()` and `getItemCount()`, we don't need to call in adapter
  * AsyncListDiffer a helper for computing differences between two lists with on DiffUtil and runs on bg thread
  
  Data binding:
  
 * Binding adapters are a good solution when you need to transform complex data.
 * `binding.executePendingBindings()` This call is an optimization that asks data binding to execute any pending bindings right away. 
    It's always a good idea to call `executePendingBindings()` when you use binding adapters in a RecyclerView, 
	because it can slightly speed up sizing the views.
 * [Codelab to understand databinding with RV using @bindingadapter for complex data]
    (https://codelabs.developers.google.com/codelabs/kotlin-android-training-diffutil-databinding/#6)

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
