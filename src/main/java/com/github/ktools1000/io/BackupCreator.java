/*******************************************************************************
 * Copyright (c) 2017 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.ktools1000.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupCreator {

    private static final Logger logger = LoggerFactory.getLogger(BackupCreator.class);

    /**
     * Copies given file to a new file with file name suffix '.bak'.
     *
     * @param file
     *            the file to make a backup of
     * @return the new file, or {@code null} in case of error
     */
    public File makeBackup(final File file) {
	if (file.exists())
	    try {
		final File backupFile = new File(file.getParentFile(), file.getName() + ".bak");
		FileUtils.copyFile(file, backupFile);
		if (logger.isDebugEnabled())
		    logger.debug("Backup created as " + backupFile);
		return backupFile;
	    } catch (final IOException e) {
		if (logger.isErrorEnabled())
		    logger.error(e.getLocalizedMessage(), e);
	    }
	return null;
    }

    public File restoreBackup(final File backupFile) {
	if (backupFile.exists() && backupFile.getName().endsWith(".bak"))
	    try {
		final String oldFileName = backupFile.getName().substring(0, backupFile.getName().length() - 4);
		final File file = new File(backupFile.getParentFile(), oldFileName);
		FileUtils.copyFile(backupFile, file);
		if (logger.isDebugEnabled())
		    logger.debug("Backup restored to " + file);
		return file;
	    } catch (final IOException e) {
		if (logger.isErrorEnabled())
		    logger.error(e.getLocalizedMessage(), e);
	    }
	return null;

    }
}
