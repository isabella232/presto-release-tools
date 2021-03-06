/*
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
 */
package com.facebook.presto.release.maven;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class MavenVersionUtil
{
    private MavenVersionUtil()
    {
    }

    public static String getVersionFromPom(File directory)
    {
        checkArgument(directory.exists(), "Does not exists: %s", directory.getAbsolutePath());
        checkArgument(directory.isDirectory(), "Not a directory: %s", directory.getAbsolutePath());
        File pomFile = Paths.get(directory.getAbsolutePath(), "pom.xml").toFile();
        try {
            Map<String, Object> elements = new XmlMapper().readValue(pomFile, new TypeReference<Map<String, Object>>() {});
            checkArgument(elements.containsKey("version"), "No version tag found in %s", pomFile.getAbsolutePath());
            return (String) elements.get("version");
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
