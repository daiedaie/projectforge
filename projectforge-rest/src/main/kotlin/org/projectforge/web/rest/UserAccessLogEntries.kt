/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2020 Micromata GmbH, Germany (www.micromata.com)
//
// ProjectForge is dual-licensed.
//
// This community edition is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation; version 3 of the License.
//
// This community edition is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, see http://www.gnu.org/licenses/.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.web.rest

private const val MAX_SIZE = 20

/**
 * Access to rest services will be logged, including UserAgent string, IP, used type of token and timestamp for
 * checking unauthorized access.
 */
class UserAccessLogEntries {
    private var entries = mutableSetOf<UserAccessLogEntry>()

    fun update(entry: UserAccessLogEntry) {
        entries.add(entry)
        if (entries.size > 20) {
            val numberOfItemsToDrop = entries.size - MAX_SIZE
            entries = sortedList().dropLast(numberOfItemsToDrop).toMutableSet()
        }
    }

    /**
     * @return All entries sorted by timeStamp of last access in descending order.
     */
    fun sortedList(): List<UserAccessLogEntry> {
        return entries.sortedByDescending { it.lastAccess }
    }

    fun size(): Int {
        return entries.size
    }
}