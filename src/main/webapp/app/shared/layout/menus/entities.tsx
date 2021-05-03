import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/voyageur">
      <Translate contentKey="global.menu.entities.voyageur" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/billet">
      <Translate contentKey="global.menu.entities.billet" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/voyage">
      <Translate contentKey="global.menu.entities.voyage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/colis">
      <Translate contentKey="global.menu.entities.colis" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      <Translate contentKey="global.menu.entities.client" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
