/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.server.fs;

import java.util.Optional;

import org.apache.accumulo.core.client.impl.Table;

public class VolumeChooserEnvironment {

  private final Optional<Table.ID> tableId;
  // scope is meant for non-table identifiers
  private String scope;

  public VolumeChooserEnvironment(Optional<Table.ID> tableId) {
    this.tableId = tableId;
  }

  public boolean hasTableId() {
    return tableId.isPresent();
  }

  public Table.ID getTableId() {
    return tableId.get();
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getScope() {
    return this.scope;
  }

  public boolean hasScope() {
    return this.scope != null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof VolumeChooserEnvironment)) {
      return false;
    }
    VolumeChooserEnvironment other = (VolumeChooserEnvironment) obj;
    if (other.hasTableId() != this.hasTableId()) {
      return false;
    }
    if (!other.getTableId().equals(this.getTableId())) {
      return false;
    }
    if (other.hasScope() != this.hasScope()) {
      return false;
    }
    if (other.hasScope() && !other.getScope().equals(this.getScope())) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return tableId.hashCode() * 31 + (this.scope == null ? 17 : this.scope.hashCode());
  }
}
