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

package org.projectforge.plugins.ihk;

import org.projectforge.plugins.core.AbstractPlugin;
import org.projectforge.plugins.core.PFPluginService;

/**
 * Created by mnuhn on 05.12.2019
 */
public class IHKPFPluginService implements PFPluginService
{

  @Override
  public String getPluginId()
  {
    return IHKPlugin.ID;
  }

  @Override
  public String getPluginName()
  {
    return "IHK";
  }

  @Override
  public String getPluginDescription()
  {
    return "IHK PF PlugIn";
  }

  @Override
  public AbstractPlugin createPluginInstance()
  {
    return new IHKPlugin();
  }

  @Override
  public boolean isBuiltIn() {
    return true;
  }
}
