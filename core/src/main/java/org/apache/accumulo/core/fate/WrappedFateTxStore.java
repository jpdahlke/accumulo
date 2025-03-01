/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.accumulo.core.fate;

import java.io.Serializable;
import java.time.Duration;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;

public class WrappedFateTxStore<T> implements FateStore.FateTxStore<T> {
  protected final FateStore.FateTxStore<T> wrapped;
  private final boolean allowForceDel;

  public WrappedFateTxStore(FateStore.FateTxStore<T> wrapped, boolean allowForceDel) {
    this.wrapped = wrapped;
    this.allowForceDel = allowForceDel;
  }

  @Override
  public void unreserve(Duration deferTime) {
    wrapped.unreserve(deferTime);
  }

  @Override
  public Repo<T> top() {
    return wrapped.top();
  }

  @Override
  public void push(Repo<T> repo) throws StackOverflowException {
    wrapped.push(repo);
  }

  @Override
  public void pop() {
    wrapped.pop();
  }

  @Override
  public FateStore.TStatus getStatus() {
    return wrapped.getStatus();
  }

  @Override
  public Optional<FateKey> getKey() {
    return wrapped.getKey();
  }

  @Override
  public void setStatus(FateStore.TStatus status) {
    wrapped.setStatus(status);
  }

  @Override
  public FateStore.TStatus waitForStatusChange(EnumSet<FateStore.TStatus> expected) {
    return wrapped.waitForStatusChange(expected);
  }

  @Override
  public void setTransactionInfo(Fate.TxInfo txInfo, Serializable val) {
    wrapped.setTransactionInfo(txInfo, val);
  }

  @Override
  public Serializable getTransactionInfo(Fate.TxInfo txInfo) {
    return wrapped.getTransactionInfo(txInfo);
  }

  @Override
  public void delete() {
    wrapped.delete();
  }

  @Override
  public void forceDelete() {
    Preconditions.checkState(allowForceDel, "Force delete is not allowed");
    wrapped.forceDelete();
  }

  @Override
  public long timeCreated() {
    return wrapped.timeCreated();
  }

  @Override
  public FateId getID() {
    return wrapped.getID();
  }

  @Override
  public List<ReadOnlyRepo<T>> getStack() {
    return wrapped.getStack();
  }
}
