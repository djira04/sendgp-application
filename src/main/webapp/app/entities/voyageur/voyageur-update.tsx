import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './voyageur.reducer';
import { IVoyageur } from 'app/shared/model/voyageur.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVoyageurUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VoyageurUpdate = (props: IVoyageurUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { voyageurEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/voyageur' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.bornDate = convertDateTimeToServer(values.bornDate);

    if (errors.length === 0) {
      const entity = {
        ...voyageurEntity,
        ...values,
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
          <h2 id="sengpApp.voyageur.home.createOrEditLabel" data-cy="VoyageurCreateUpdateHeading">
            <Translate contentKey="sengpApp.voyageur.home.createOrEditLabel">Create or edit a Voyageur</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : voyageurEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="voyageur-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="voyageur-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstnameLabel" for="voyageur-firstname">
                  <Translate contentKey="sengpApp.voyageur.firstname">Firstname</Translate>
                </Label>
                <AvField id="voyageur-firstname" data-cy="firstname" type="text" name="firstname" />
              </AvGroup>
              <AvGroup>
                <Label id="lastnameLabel" for="voyageur-lastname">
                  <Translate contentKey="sengpApp.voyageur.lastname">Lastname</Translate>
                </Label>
                <AvField id="voyageur-lastname" data-cy="lastname" type="text" name="lastname" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="voyageur-email">
                  <Translate contentKey="sengpApp.voyageur.email">Email</Translate>
                </Label>
                <AvField id="voyageur-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="passwordLabel" for="voyageur-password">
                  <Translate contentKey="sengpApp.voyageur.password">Password</Translate>
                </Label>
                <AvField id="voyageur-password" data-cy="password" type="text" name="password" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="voyageur-telephone">
                  <Translate contentKey="sengpApp.voyageur.telephone">Telephone</Translate>
                </Label>
                <AvField id="voyageur-telephone" data-cy="telephone" type="string" className="form-control" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="bornDateLabel" for="voyageur-bornDate">
                  <Translate contentKey="sengpApp.voyageur.bornDate">Born Date</Translate>
                </Label>
                <AvInput
                  id="voyageur-bornDate"
                  data-cy="bornDate"
                  type="datetime-local"
                  className="form-control"
                  name="bornDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.voyageurEntity.bornDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cinLabel" for="voyageur-cin">
                  <Translate contentKey="sengpApp.voyageur.cin">Cin</Translate>
                </Label>
                <AvField id="voyageur-cin" data-cy="cin" type="text" name="cin" />
              </AvGroup>
              <AvGroup>
                <Label id="photoLabel" for="voyageur-photo">
                  <Translate contentKey="sengpApp.voyageur.photo">Photo</Translate>
                </Label>
                <AvField id="voyageur-photo" data-cy="photo" type="text" name="photo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/voyageur" replace color="info">
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
  voyageurEntity: storeState.voyageur.entity,
  loading: storeState.voyageur.loading,
  updating: storeState.voyageur.updating,
  updateSuccess: storeState.voyageur.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VoyageurUpdate);
