
Introduction
------------

TrackMySleepQuality is an app for recording sleep data for each night. 
You can record a start and stop time, assign a quality rating, and clear the database. 

You will learn
--------------

* How to use ViewModel with Databinding to get rid of click handlers from UI
* Using transformations to transform livedata to string object for further uses
  e.g getting livedata object from room, applying transformations on them.
* Handling button toggle states using livedata making use of `android: enabled` attribute
* Using coroutines to run db tasks and using learn switching from ui thread to IO for heavy tasks
* Learn to use viewmodelFactory that helps in giving one instance of VM for a UI lifecycle. 
* How to use livedata for navigation triggering.


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
