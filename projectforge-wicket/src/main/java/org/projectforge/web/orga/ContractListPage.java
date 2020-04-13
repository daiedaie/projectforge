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

package org.projectforge.web.orga;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.projectforge.business.orga.Contract;
import org.projectforge.business.orga.ContractDao;
import org.projectforge.framework.utils.NumberHelper;
import org.projectforge.web.wicket.*;

import java.util.ArrayList;
import java.util.List;

@ListPage(editPage = ContractEditPage.class)
public class ContractListPage extends AbstractListPage<ContractListForm, ContractDao, Contract>
    implements IListPageColumnsCreator<Contract>
{
  private static final long serialVersionUID = 671935723386728113L;

  @SpringBean
  private ContractDao contractDao;

  public ContractListPage(final PageParameters parameters)
  {
    super(parameters, "legalAffaires.contract");
  }

  /**
   * @see org.projectforge.web.wicket.IListPageColumnsCreator#createColumns(org.apache.wicket.markup.html.WebPage,
   *      boolean)
   */
  @SuppressWarnings("serial")
  @Override
  public List<IColumn<Contract, String>> createColumns(final WebPage returnToPage, final boolean sortable)
  {
    final List<IColumn<Contract, String>> columns = new ArrayList<IColumn<Contract, String>>();

    final CellItemListener<Contract> cellItemListener = new CellItemListener<Contract>()
    {
      public void populateItem(final Item<ICellPopulator<Contract>> item, final String componentId,
                               final IModel<Contract> rowModel)
      {
        final Contract contract = rowModel.getObject();
        appendCssClasses(item, contract.getId(), contract.isDeleted());
      }
    };
    columns.add(
        new CellItemListenerPropertyColumn<Contract>(getString("legalAffaires.contract.number"), "number", "number",
            cellItemListener)
        {
          @SuppressWarnings({ "unchecked", "rawtypes" })
          @Override
          public void populateItem(final Item item, final String componentId, final IModel rowModel)
          {
            final Contract contract = (Contract) rowModel.getObject();
            item.add(
                new ListSelectActionPanel(componentId, rowModel, ContractEditPage.class, contract.getId(), returnToPage,
                    NumberHelper.getAsString(contract.getNumber())));
            cellItemListener.populateItem(item, componentId, rowModel);
            addRowClick(item);
          }
        });
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("date"), "date", "date", cellItemListener));
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("legalAffaires.contract.type"), "type", "type",
        cellItemListener));
    columns
        .add(new CellItemListenerPropertyColumn<Contract>(getString("status"), "status", "status", cellItemListener));
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("title"), "title", "title", cellItemListener));
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("legalAffaires.contract.coContractorA"),
        "coContractorA",
        "coContractorA", cellItemListener));
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("legalAffaires.contract.coContractorB"),
        "coContractorB",
        "coContractorB", cellItemListener));
    columns.add(new CellItemListenerPropertyColumn<Contract>(getString("resubmissionOnDate"), "resubmissionOnDate",
        "resubmissionOnDate",
        cellItemListener));
    columns.add(
        new CellItemListenerPropertyColumn<Contract>(getString("dueDate"), "dueDate", "dueDate", cellItemListener));
    return columns;
  }

  @Override
  protected void init()
  {
    dataTable = createDataTable(createColumns(this, true), "number", SortOrder.DESCENDING);
    form.add(dataTable);
  }

  @Override
  protected ContractListForm newListForm(final AbstractListPage<?, ?, ?> parentPage)
  {
    return new ContractListForm(this);
  }

  @Override
  public ContractDao getBaseDao()
  {
    return contractDao;
  }
}
