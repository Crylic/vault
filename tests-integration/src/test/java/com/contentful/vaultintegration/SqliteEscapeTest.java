package com.contentful.vaultintegration;

import com.contentful.vault.Vault;
import com.contentful.vaultintegration.lib.escape.SqliteEscapeModel;
import com.contentful.vaultintegration.lib.escape.SqliteEscapeSpace;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;

public class SqliteEscapeTest extends BaseTest {
  @Override protected void setupVault() {
    vault = Vault.with(RuntimeEnvironment.application, SqliteEscapeSpace.class);
  }

  @Test public void testSync() throws Exception {
    enqueue("sqlite_escape_space.json");
    enqueue("sqlite_escape_initial.json");
    sync();

    SqliteEscapeModel item = vault.fetch(SqliteEscapeModel.class).first();
    assertItem(item);

    item = vault.fetch(SqliteEscapeModel.class).where("`order` = ?", "foo").first();
    assertItem(item);
  }

  private void assertItem(SqliteEscapeModel first) {
    assertThat(first).isNotNull();
    assertThat(first.order).isEqualTo("foo");
  }
}