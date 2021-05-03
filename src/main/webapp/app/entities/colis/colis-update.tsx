import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVoyage } from 'app/shared/model/voyage.model';
import { getEntities as getVoyages } from 'app/entities/voyage/voyage.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './colis.reducer';
import { IColis } from 'app/shared/model/colis.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IColisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ColisUpdate = (props: IColisUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { colisEntity, voyages, clients, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/colis' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVoyages();
    props.getClients();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...colisEntity,
        ...values,
        voyage: voyages.find(it => it.id.toString() === values.voyageId.toString()),
        client: clients.find(it => it.id.toString() === values.clientId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sengpApp.colis.home.createOrEditLabel" data-cy="ColisCreateUpdateHeading">
            <Translate contentKey="sengpApp.colis.home.createOrEditLabel">Create or edit a Colis</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : colisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="colis-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="colis-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="weightLabel" for="colis-weight">
                  <Translate contentKey="sengpApp.colis.weight">Weight</Translate>
                </Label>
                <AvField id="colis-weight" data-cy="weight" type="string" className="form-control" name="weight" />
              </AvGroup>
              <AvGroup>
                <Label id="detailsLabel" for="colis-details">
                  <Translate contentKey="sengpApp.colis.details">Details</Translate>
                </Label>
                <AvField id="colis-details" data-cy="details" type="text" name="details" />
              </AvGroup>
              <AvGroup>
                <Label for="colis-voyage">
                  <Translate contentKey="sengpApp.colis.voyage">Voyage</Translate>
                </Label>
                <AvInput id="colis-voyage" data-cy="voyage" type="select" className="form-control" name="voyageId">
                  <option value="" key="0" />
                  {voyages
                    ? voyages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="colis-client">
                  <Translate contentKey="sengpApp.colis.client">Client</Translate>
                </Label>
                <AvInput id="colis-client" data-cy="client" type="select" className="form-control" name="clientId">
                  <option value="" key="0" />
                  {clients
                    ? clients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/colis" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  voyages: storeState.voyage.entities,
  clients: storeState.client.entities,
  colisEntity: storeState.colis.entity,
  loading: storeState.colis.loading,
  updating: storeState.colis.updating,
  updateSuccess: storeState.colis.updateSuccess,
});

const mapDispatchToProps = {
  getVoyages,
  getClients,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ColisUpdate);
