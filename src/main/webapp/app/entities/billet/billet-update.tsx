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
import { IVoyageur } from 'app/shared/model/voyageur.model';
import { getEntities as getVoyageurs } from 'app/entities/voyageur/voyageur.reducer';
import { getEntity, updateEntity, createEntity, reset } from './billet.reducer';
import { IBillet } from 'app/shared/model/billet.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBilletUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BilletUpdate = (props: IBilletUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { billetEntity, voyages, voyageurs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/billet' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVoyages();
    props.getVoyageurs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...billetEntity,
        ...values,
        voyageur: voyageurs.find(it => it.id.toString() === values.voyageurId.toString()),
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
          <h2 id="sengpApp.billet.home.createOrEditLabel" data-cy="BilletCreateUpdateHeading">
            <Translate contentKey="sengpApp.billet.home.createOrEditLabel">Create or edit a Billet</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : billetEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="billet-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="billet-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="labelLabel" for="billet-label">
                  <Translate contentKey="sengpApp.billet.label">Label</Translate>
                </Label>
                <AvField id="billet-label" data-cy="label" type="text" name="label" />
              </AvGroup>
              <AvGroup>
                <Label id="urlLabel" for="billet-url">
                  <Translate contentKey="sengpApp.billet.url">Url</Translate>
                </Label>
                <AvField id="billet-url" data-cy="url" type="text" name="url" />
              </AvGroup>
              <AvGroup>
                <Label id="websiteLabel" for="billet-website">
                  <Translate contentKey="sengpApp.billet.website">Website</Translate>
                </Label>
                <AvField id="billet-website" data-cy="website" type="text" name="website" />
              </AvGroup>
              <AvGroup>
                <Label for="billet-voyageur">
                  <Translate contentKey="sengpApp.billet.voyageur">Voyageur</Translate>
                </Label>
                <AvInput id="billet-voyageur" data-cy="voyageur" type="select" className="form-control" name="voyageurId">
                  <option value="" key="0" />
                  {voyageurs
                    ? voyageurs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/billet" replace color="info">
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
  voyageurs: storeState.voyageur.entities,
  billetEntity: storeState.billet.entity,
  loading: storeState.billet.loading,
  updating: storeState.billet.updating,
  updateSuccess: storeState.billet.updateSuccess,
});

const mapDispatchToProps = {
  getVoyages,
  getVoyageurs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BilletUpdate);
