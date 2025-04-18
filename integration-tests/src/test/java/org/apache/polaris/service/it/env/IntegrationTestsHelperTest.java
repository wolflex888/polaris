/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.polaris.service.it.env;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IntegrationTestsHelperTest {

  @ParameterizedTest
  @MethodSource
  void getTemporaryDirectory(String envVar, Path local, URI expected) {
    URI actual = IntegrationTestsHelper.getTemporaryDirectory(s -> envVar, local);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> getTemporaryDirectory() {
    return Stream.of(
        Arguments.of(null, Path.of("/tmp/polaris"), URI.create("file:///tmp/polaris/")),
        Arguments.of(null, Path.of("/tmp/polaris/"), URI.create("file:///tmp/polaris/")),
        Arguments.of(
            "file:///tmp/polaris/from-env",
            Path.of("/tmp/polaris/default"),
            URI.create("file:///tmp/polaris/from-env/")),
        Arguments.of(
            "file:///tmp/polaris/from-env/",
            Path.of("/tmp/polaris/default"),
            URI.create("file:///tmp/polaris/from-env/")),
        Arguments.of(
            "/tmp/polaris/from-env",
            Path.of("/tmp/polaris/default"),
            URI.create("file:///tmp/polaris/from-env/")),
        Arguments.of(
            "/tmp/polaris/from-env/",
            Path.of("/tmp/polaris/default"),
            URI.create("file:///tmp/polaris/from-env/")),
        Arguments.of(
            "s3://bucket/polaris/from-env",
            Path.of("/tmp/polaris/default"),
            URI.create("s3://bucket/polaris/from-env/")),
        Arguments.of(
            "s3://bucket/polaris/from-env/",
            Path.of("/tmp/polaris/default"),
            URI.create("s3://bucket/polaris/from-env/")));
  }
}
