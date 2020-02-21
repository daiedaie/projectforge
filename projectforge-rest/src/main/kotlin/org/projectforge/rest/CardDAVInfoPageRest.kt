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

package org.projectforge.rest

import org.projectforge.business.user.UserAuthenticationsService
import org.projectforge.business.user.UserTokenType
import org.projectforge.framework.i18n.translate
import org.projectforge.framework.persistence.user.api.ThreadLocalUserContext
import org.projectforge.rest.config.Rest
import org.projectforge.ui.UICol
import org.projectforge.ui.UIFieldset
import org.projectforge.ui.UILabel
import org.projectforge.ui.UILayout
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${Rest.URL}/cardDAVInfo")
class CardDAVInfoPageRest {

    @Autowired
    private lateinit var authenticationsService: UserAuthenticationsService

    @GetMapping("layout")
    fun getLayout(@RequestParam("type") type: String?): UILayout {
        val layout = UILayout("address.cardDAV.infopage.title")
                .add(UIFieldset(length = 12)
                        .add(UILabel(translate("address.cardDAV.infopage.description")))
                        .add(UICol(length = 6)
                                .add(UILabel(translate("user"))))
                        .add(UICol(length = 6)
                                .add(UILabel(ThreadLocalUserContext.getUser()?.username)))
                        .add(UICol(length = 6)
                                .add(UILabel(translate("password"))))
                        .add(UICol(length = 6)
                                .add(UILabel(authenticationsService.getToken(ThreadLocalUserContext.getUserId(), UserTokenType.DAV_TOKEN)))))
        return layout
    }
}