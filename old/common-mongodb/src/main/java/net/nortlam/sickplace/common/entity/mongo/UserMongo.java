/*
 * 
 * Copyright 2014 Mauricio "Maltron" Leal <maltron@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package net.nortlam.sickplace.common.entity.mongo;

import net.nortlam.sickplace.common.MongoDB;
import net.nortlam.sickplace.common.entity.User;
import org.bson.Document;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class UserMongo extends User implements MongoDB {

    public UserMongo() {
    }

    // MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB 
    //   MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB MONGODB 
    @Override
    public Document toDocument() {
        Document document = new Document(PROPERTY_ID, getID())
                .append(PROPERTY_EMAIL, getEmail())
                .append(PROPERTY_PASSWORD, getPassword());
        
        return document;
    }
}
