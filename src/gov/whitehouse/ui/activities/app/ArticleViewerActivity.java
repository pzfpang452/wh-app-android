/*
 * This project constitutes a work of the United States Government and is
 * not subject to domestic copyright protection under 17 USC § 105.
 * 
 * However, because the project utilizes code licensed from contributors
 * and other third parties, it therefore is licensed under the MIT
 * License.  http://opensource.org/licenses/mit-license.php.  Under that
 * license, permission is granted free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the conditions that any appropriate copyright notices and this
 * permission notice are included in all copies or substantial portions
 * of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package gov.whitehouse.ui.activities.app;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import gov.whitehouse.R;
import gov.whitehouse.core.FeedItem;
import gov.whitehouse.ui.activities.BaseActivity;
import gov.whitehouse.ui.fragments.app.ArticleViewerFragment;
import gov.whitehouse.utils.GATrackingManager;
import gov.whitehouse.utils.GsonUtils;

import static android.view.View.GONE;

public class ArticleViewerActivity extends BaseActivity {

    ArticleViewerFragment mFragment;

    FeedItem mFeedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        mFragment = new ArticleViewerFragment();
        Bundle extras = getIntent().getExtras();
        mFragment.setArguments(extras);

        final String json = extras.getString("item_json");
        if (json != null) {
            mFeedItem = GsonUtils.fromJson(json, FeedItem.class);
        }

        ft.replace(R.id.main_container, mFragment);

        ft.commit();

        if (isMultipaned()) {
            findViewById(R.id.details_container).setVisibility(GONE);
            findViewById(R.id.details_shadow).setVisibility(GONE);
        }
    }

    @Override
    public void trackPageView() {
        GATrackingManager.getInstance().track(getTrackingPathComponent(), mFeedItem.getTitle());
    }
}
