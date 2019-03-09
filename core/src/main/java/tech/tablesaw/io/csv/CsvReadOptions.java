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

package tech.tablesaw.io.csv;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.io.ReadOptions;

@Getter @Accessors(chain = true, fluent = true)
public class CsvReadOptions extends ReadOptions {

    private final ColumnType[] columnTypes;
    private final Character separator;
    private final String lineEnding;
    private final Integer maxNumberOfColumns;
    private final Character commentPrefix;

    private CsvReadOptions(CsvReadOptions.Builder builder) {
	super(builder);

        columnTypes = builder.columnTypes;
        separator = builder.separator;
        lineEnding = builder.lineEnding;
        maxNumberOfColumns = builder.maxNumberOfColumns;
        commentPrefix = builder.commentPrefix;
    }

    public static Builder builder(File file) {
        return new Builder(file).tableName(file.getName());
    }

    public static Builder builder(String fileName) {
        return new Builder(new File(fileName));
    }

    /**
     * This method may cause tablesaw to buffer the entire InputStream.
     * <p>
     * If you have a large amount of data, you can do one of the following:
     * 1. Use the method taking a File instead of a stream, or
     * 2. Provide the array of column types as an option. If you provide the columnType array,
     * we skip type detection and can avoid reading the entire file
     */
    public static Builder builder(InputStream stream, String tableName) {
        return new Builder(stream).tableName(tableName);
    }

    /**
     * This method may cause tablesaw to buffer the entire InputStream.
     *
     * <p>
     * If you have a large amount of data, you can do one of the following:
     * 1. Use the method taking a File instead of a reader, or
     * 2. Provide the array of column types as an option. If you provide the columnType array,
     * we skip type detection and can avoid reading the entire file
     */
    public static Builder builder(Reader reader, String tableName) {
        Builder builder = new Builder(reader);
        return builder.tableName(tableName);
    }

    @Getter @Setter @Accessors(chain = true, fluent = true)
    public static class Builder extends ReadOptions.Builder {

        // specific fields
        protected Character separator = ',';
        protected String lineEnding;
        protected ColumnType[] columnTypes;
        protected Integer maxNumberOfColumns = 10_000;
        protected Character commentPrefix;

        protected Builder(File file) {
            super(file);
        }

        protected Builder(Reader reader) {
            super(reader);
        }

        protected Builder(InputStream stream) {
            super(stream);
        }
        
        /**
         * Defines maximal value of columns in csv file.
         * @param maxNumberOfColumns - must be positive integer. Default is 512.
         */
        public Builder maxNumberOfColumns(Integer maxNumberOfColumns) {
            this.maxNumberOfColumns = maxNumberOfColumns;
            return this;
        }

        public CsvReadOptions build() {
            return new CsvReadOptions(this);
        }
        
        // Override super-class setters to return an instance of this class

        @Override
        public Builder header(boolean header) {
            super.header(header);
            return this;
        }

        @Override
        public Builder tableName(String tableName) {
            super.tableName(tableName);
            return this;
        }

        @Override
        public Builder sample(boolean sample) {
            super.sample(sample);
            return this;
        }

        @Override
        public Builder dateFormat(String dateFormat) {
            super.dateFormat(dateFormat);
            return this;
        }

        @Override
        public Builder timeFormat(String timeFormat) {
            super.timeFormat(timeFormat);
            return this;
        }

        @Override
        public Builder dateTimeFormat(String dateTimeFormat) {
            super.dateTimeFormat(dateTimeFormat);
            return this;
        }

        @Override
        public Builder locale(Locale locale) {
            super.locale(locale);
            return this;
        }

        @Override
        public Builder missingValueIndicator(String missingValueIndicator) {
            super.missingValueIndicator(missingValueIndicator);
            return this;
        }

        @Override
        public Builder minimizeColumnSizes(boolean minimizeColumnSizes) {
            super.minimizeColumnSizes(minimizeColumnSizes);
            return this;
        }
    }

}
