/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2019 Micromata GmbH, Germany (www.micromata.com)
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

package org.projectforge.framework.persistence.api.impl

import org.projectforge.framework.ToStringUtil
import org.projectforge.framework.persistence.api.BaseDao
import org.projectforge.framework.persistence.api.SortProperty

/**
 * DBFilter is created by QueryFilter and hold all predicates for building a query.
 */
class DBFilter(
        var sortAndLimitMaxRowsWhileSelect: Boolean = true,
        var maxRows: Int = 50) {
    /**
     * Statistics are needed to evaluate which query should be used (full-text or criteria search).
     */
    class Statistics {
        var fullTextRequired: Boolean = false
        var numberOfCriteriaPredicates = 0
        var numberOfFullTextQueries = 0
        var numberOfResultPredicates = 0
    }

    val predicates = mutableListOf<DBPredicate>()

    val sortProperties = mutableListOf<SortProperty>()


    fun createStatistics(baseDao: BaseDao<*>): Statistics {
        var fullTextRequired = false
        for (it in predicates) {
            if (!it.criteriaSupport && !it.resultSetSupport) {
                fullTextRequired = true
                break
            }
        }
        val stats = Statistics()
        stats.fullTextRequired = fullTextRequired
        if (fullTextRequired) {
            val indexedSearchFields = DBQueryBuilderByFullText.getUsedSearchFields(baseDao)
            predicates.forEach { predicate ->
                if (predicate.fullTextSupport
                        && (predicate.field == null || indexedSearchFields.any { it == predicate.field })) {
                    ++stats.numberOfFullTextQueries
                } else {
                    ++stats.numberOfResultPredicates
                }
            }
        } else {
            predicates.forEach { predicate ->
                if (predicate.criteriaSupport) {
                    ++stats.numberOfCriteriaPredicates
                } else {
                    ++stats.numberOfResultPredicates
                }
            }
        }
        return stats
    }

    @Transient
    internal val log = org.slf4j.LoggerFactory.getLogger(DBFilter::class.java)

    override fun toString(): String {
        return ToStringUtil.toJsonString(this)
    }
}
