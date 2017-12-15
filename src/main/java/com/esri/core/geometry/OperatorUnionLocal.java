/*
 Copyright 1995-2015 Esri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts Dept
 380 New York Street
 Redlands, California, USA 92373

 email: contracts@esri.com
 */

package com.esri.core.geometry;

import java.util.ArrayDeque;

class OperatorUnionLocal extends OperatorUnion {

    @Override
    public GeometryCursor execute(GeometryCursor inputGeometries,
                                  SpatialReference sr, ProgressTracker progressTracker) {
        return new OperatorUnionCursor(inputGeometries, sr, progressTracker);
    }

    @Override
    public Geometry execute(Geometry geom1, Geometry geom2,
                            SpatialReference sr, ProgressTracker progressTracker) {
        ArrayDeque<Geometry> geometryArrayDeque = new ArrayDeque<>();
        geometryArrayDeque.add(geom1);geometryArrayDeque.add(geom2);
        SimpleGeometryCursor inputGeometries = new SimpleGeometryCursor(geometryArrayDeque);
        GeometryCursor outputCursor = execute(inputGeometries, sr, progressTracker);

        return outputCursor.next();
    }

}
