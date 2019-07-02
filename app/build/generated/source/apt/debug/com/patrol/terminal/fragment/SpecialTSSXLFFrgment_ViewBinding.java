// Generated code from Butter Knife. Do not modify!
package com.patrol.terminal.fragment;

import android.view.View;
import android.widget.ListView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.patrol.terminal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SpecialTSSXLFFrgment_ViewBinding implements Unbinder {
  private SpecialTSSXLFFrgment target;

  @UiThread
  public SpecialTSSXLFFrgment_ViewBinding(SpecialTSSXLFFrgment target, View source) {
    this.target = target;

    target.item_tssx_lv = Utils.findRequiredViewAsType(source, R.id.item_tssx_lv, "field 'item_tssx_lv'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SpecialTSSXLFFrgment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.item_tssx_lv = null;
  }
}
