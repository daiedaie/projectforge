package org.projectforge.rest.calendar

import org.projectforge.business.teamcal.admin.TeamCalDao
import org.projectforge.business.teamcal.event.TeamEventDao
import org.projectforge.business.teamcal.event.TeamEventFilter
import org.projectforge.business.teamcal.event.model.TeamEventDO
import org.projectforge.rest.config.Rest
import org.projectforge.rest.core.AbstractStandardRest
import org.projectforge.ui.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${Rest.URL}/teamEvent")
class TeamEventRest() : AbstractStandardRest<TeamEventDO, TeamEventDO, TeamEventDao, TeamEventFilter>(
        TeamEventDao::class.java,
        TeamEventFilter::class.java,
        "plugins.teamcal.event.title") {

    @Autowired
    private lateinit var teamCalDao: TeamCalDao

    override protected fun onGetItemAndLayout(request: HttpServletRequest, item: TeamEventDO, editLayoutData: AbstractStandardRest.EditLayoutData) {
        val recurrentDateString = request.getParameter("recurrentDate")
        println("TeamEventRest: recurrentDate=$recurrentDateString")
    }

    override fun afterEdit(obj: TeamEventDO): ResponseAction {
        return ResponseAction("calendar").addVariable("id", obj.id ?: -1)
    }

    /**
     * LAYOUT List page
     */
    override fun createListLayout(): UILayout {
        val layout = super.createListLayout()
                .add(UITable.UIResultSetTable()
                        .add(lc, "subject"))
        return LayoutUtils.processListPage(layout)
    }

    /**
     * LAYOUT Edit page
     */
    override fun createEditLayout(dataObject: TeamEventDO): UILayout {
        val calendars = teamCalDao.getAllCalendarsWithFullAccess()
        val calendarSelectValues = calendars.map { it ->
            UISelectValue<Int>(it.id, it.title)
        }
        val subject = UIInput("subject", lc)
        subject.focus = true
        val layout = super.createEditLayout(dataObject)
        if (dataObject.hasRecurrence()) {
            layout.add(UIFieldset(12, title = "plugins.teamcal.event.recurrence.change.text")
                    .add(UIGroup()
                            .add(UICheckbox("all", label = "plugins.teamcal.event.recurrence.change.text.all"))
                            .add(UICheckbox("future", label = "plugins.teamcal.event.recurrence.change.future"))
                            .add(UICheckbox("single", label = "plugins.teamcal.event.recurrence.change.single"))
                    ))
        }
        layout.add(UIFieldset(12)
                .add(UIRow()
                        .add(UICol(6)
                                .add(UISelect<Int>("calendar", values = calendarSelectValues.toMutableList(), label = "plugins.teamcal.event.teamCal"))
                                .add(subject)
                                .add(lc, "attendees")
                                .add(lc, "location", "note"))
                        .add(UICol(6)
                                .add(lc, "startDate", "endDate", "allDay")
                                .add(UIFieldset(12)
                                        .add(UICustomized("reminder")))
                                .add(UIFieldset(12)
                                        .add(UICustomized("recurrence"))))))

        return LayoutUtils.processEditPage(layout, dataObject)
    }
}
