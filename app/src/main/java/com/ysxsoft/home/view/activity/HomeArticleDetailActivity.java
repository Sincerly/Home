package com.ysxsoft.home.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ysxsoft.common_base.adapter.BaseQuickAdapter;
import com.ysxsoft.common_base.adapter.BaseViewHolder;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.base.frame.list.IListAdapter;
import com.ysxsoft.common_base.base.frame.list.ListManager;
import com.ysxsoft.common_base.okhttp.HttpResponse;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.utils.WebViewUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.home.R;
import com.ysxsoft.home.config.AppConfig;
import com.ysxsoft.home.response.ArticleDetailResponse;
import com.ysxsoft.home.view.dialog.ShareDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by Sincerly on 2019/5/17 0017
 **/
@Route(path = "/main/HomeArticleDetailActivity")
public class HomeArticleDetailActivity extends BaseActivity implements IListAdapter<String> {
    @Autowired
    String nid;
    @Autowired
    String tname;
    TextView articleTitle;
    CircleImageView logo;
    TextView subname;
    TextView address;
    WebView webView;
    TextView sayNum;
    TextView likeNum;
    TextView all;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customCenterTabView)
    LinearLayout customCenterTabView;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    ListManager<String> manager;
    private View headerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_home_detail;
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initList();
    }

    private void initList() {
        manager = new ListManager(this);
        manager.init(getWindow().findViewById(android.R.id.content));
        manager.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //评论
            }
        });
        if(headerView==null){
            manager.getAdapter().addHeaderView(createHeaderView());
            WebViewUtils.init(webView);
            WebViewUtils.setH5Data(webView, "<p><strong><span style=\"font-size:21px;font-family: 黑体;color:#444444;background:white\">&nbsp; &nbsp;树欲静而风不止，子欲养而亲不在</span></strong></p><p><strong><span style=\"font-size:21px;font-family:黑体;color:#444444\"><img src=\"http://zhyuce.sanzhima.cn/uploads/images/20190514/34f1cb077d26b74ddfecd73bff9e463c.jpg\" title=\"0ed3c87284fd649414d5966cfb3961e.jpg\" alt=\"\"/> </span></strong><span style=\"font-size:16px;font-family:&#39;寰\uE1C0敓鏂ゆ嫹閿熻剼鐚存嫹&#39;,&#39;serif&#39;;color:#444444\"><br/> </span><span style=\"font-size:18px;font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">结婚那天，酒店门前车水马龙。</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> &nbsp;&nbsp;&nbsp; </span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">妈妈问我：坐在角落里象两个要饭模样的人是谁？<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我看过去的时候，有个老头正盯着我，旁边还有个老太太，发现我看着他们时赶忙低下头。我不认识他们但看起来也不像是要饭的，衣服是新的，连折印都看得出来。妈说他们像是要饭的，是因为他们佝偻着身子，老太的身边还倚了根拐杖的缘故。</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">妈说天池是孤儿，那边没亲戚来，如果不认识，就轰他们走吧。现在要饭的坏着呢，喜欢等在酒店门口，见哪家办喜事就装作亲戚来吃黑酒。</span></p><p style=\"background: white\"><span style=\"font-size:18px;font-family:宋体;color:#464646\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646\">我说不会，叫来天池问一下吧？天池慌里慌张地把我的手捧花都掉地上了，最后吱吱唔唔地说是他们家堂叔和堂婶。我瞪了妈妈一眼：差点把亲戚赶走。</span><span style=\"font-size:18px;font-family:宋体;color:#464646\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646\">妈说，天池你不是孤儿吗？哪来的亲戚呢？<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646\">天池怕妈，低头说是他家远房的亲戚，好长时间不来往了。但结婚是大事，家里一个亲戚没来心里觉着是个憾事，所以……</span></p><p><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我靠着天池的肩，埋怨他有亲戚来也不早说，应该把他们调一桌，既然是亲戚就不能坐在备用桌上。天池拦着说就让他们坐那吧，坐别桌他们吃着也不自在。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">直到开席，那桌上也就坐了堂叔和堂婶。敬谢席酒时经过那桌，天池犹豫了一下拉着我从他们身边擦了过去。回头看到他们的头埋的很低，想了想我把天池给拽了回去：堂叔、堂婶，我们给你俩敬酒了！<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">两人抬起头有点不相信的盯着我。二老的头发都是花白的，看上去很老应该有七八十岁的样子，堂婶的眼睛很空洞，脸虽对着我但眼神闪忽不定。我拿手不确定的在她眼前晃了晃，没反应。原来堂婶是个瞎子。</span><span style=\"font-size:18px;font-family:黑体;color:#444444;background:white\"><br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">堂叔、堂婶，这是俺媳妇小洁，俺们现在给你们敬酒呢！天池在用乡音提醒他们。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">哦、哦,堂叔歪歪斜斜地站了起来，左手扶着堂婶的肩右手颤微微地端起酒杯，手指背上都是黄黄的茧，厚厚的指夹逢里留着黑黑的泥。面朝黄土背朝天的日子让他们过早地累弯了腰。我惊讶地发现，堂叔的右腿是空的。堂婶是瞎子，堂叔是瘸子，怎样的一对夫妻啊？别站了，你们坐下吧。我走过去扶住他们。堂叔又摇晃着坐下了，无缘由的堂婶眼里忽然就叭嗒叭嗒直掉泪，看到堂叔无言地拍着她的背。本想劝他们两句，但天池拉着我离开了。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我跟天池说，等他们回家的时候给他们一点钱吧，太可怜了。两人都是残疾，这日子根本想不通怎么过。天池点点头没说话，紧紧拥着我。</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/><img src=\"http://zhyuce.sanzhima.cn/uploads/images/20190514/0e13a2eb04f24efb7c33c0136458d9d5.jpg\" title=\"3e734d1c464db1dc7bf2bcbdab9d444.jpg\" alt=\"\"/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">&nbsp; &nbsp; 第一年的除夕，天池说胃疼没吃下晚饭回房睡觉去了。我让妈妈熬点大米粥也跟着进了房。天池躺在床上，眼里还憋着泪。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我说天池不带这样的，第一年的除夕就不跟我们一块吃晚饭，还跑房里这样。好象我们家亏待你似的，一过节你就胃疼，哪有这样的事情？其实我知道你不是胃疼，说吧，什么事？<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">天池闷了半天说，对不起，他只是想起堂叔和堂婶还有他死去的爹娘。他怕在桌上忍不住，惹爸妈不高兴才推说胃疼。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我搂着他说：真是个傻孩子，想他们，我们过完年看他们去就成了，再说我也想知道他俩是怎么过日子的。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">天池说，算了，那条山路特别难走，你会累着的，等以后路通了我们生了小孩再带你去那看他们吧。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我心里想说：等我们生小孩的时候他们还不一定在呢！但没敢讲出来，嘴上说给他们再寄些钱物吧！</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">第二年的中秋期间我正巧在外出差，中秋节那天又回不了家。我特别想天池和爸妈，我就跟天池煲电话粥。我问天池想我想得睡不着怎么办？天池说就上网或者看电视，再不行就睡那，睁着眼睛狠狠地想。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">那晚，我们直到把手机聊得发烫没电为止。躺在宾馆的床上，看着窗外圆圆的月亮，我怎么也睡不着。睁着眼睛流着泪想天池、想爸爸、想妈妈。</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">想到天池估计也没睡着，说不定正在网上神游。翻身我也打开电脑，重新申请了一QQ号名叫“读你”想捉弄一下天池。查了一下，天池果然在，我主动加了他，他接受了。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我问他：这样一个万家团圆的好日子，你为什么还在网上闲逛呢？</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">他说：因为我老婆在外出差，想她睡不着觉所以就上网看看。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我挺满意这句话，接着又打出：老婆不在家，可以找个情人代替，比如说网上，聊以****一下。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family: 黑体;color:#464646;background:white\">半天他才敲出一行：如果你想找情人的话，对不起，我不是你找的人，再见。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size: 18px;font-family:黑体;color:#464646;background:white\">对不起，我不是那个意思，你别生气。叭叭叭，我赶紧发过去。过了一会他问我：你怎么也在网上闲逛呢？我说：我在外打工，现在想爸爸和妈妈。刚刚和男朋友通完电话还是睡不着，就上网了。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;</span></p><p><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我也想我爹和娘，只是，亲在外，子欲养而不能。亲在外，子欲养而不能。怎么讲？我把这句话又重复敲了过去。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我有点莫明其妙，天池怎么说这样的话？</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">你叫“读你”，我今天就让你读一次吧。有些事情放在心里很久会得病，拿出来晒晒会舒服些，反正你我也不认识，你就当作听一个故事吧！于是，我意外地知道了天池一直隐藏在内心的事情。</span></p><p><span style=\"font-size: 18px; font-family: 黑体; background: white;\"><span style=\"color:#464646\"><img src=\"http://zhyuce.sanzhima.cn/uploads/images/20190514/1169c20ffa5fa78c33ad9dddfc65d9b5.jpg\" title=\"e4cf6c91cc6a6c20ae64fba7bea076c.jpg\" alt=\"\"/></span> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">30</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">年前，我爹快五十了还没娶亲，因为他腿瘸加上家里又穷没有姑娘愿意嫁他。后来，庄上来了个要饭的老头还搀着个瞎眼的女人。老头病得很重，爹看他们可怜就让他们在自家歇息。没想到一住下那老头就没起来过，后来老头的女儿就是那瞎眼的女人嫁给了我爹。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">第二年生下了我。</span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;</span><span style=\"font-size: 18px;font-family:黑体;color:#464646;background:white\">我家的日子过得很清苦，可我从来没饿过一顿。爹和娘种不了田，没有收入就帮别人家剥玉米粒，一天剥下来十指全是血泡，第二天缠上布条再剥。为了我上学，家里养了三只鸡，两只鸡生蛋卖钱，留下一只生蛋我吃。娘说她在城里要饭时听说城里的娃上学都吃鸡蛋，咱家娃也吃，将来比城里的娃更聪明。但他们从来都不吃，有回我看见娘把蛋打进锅里后用嘴舔着蛋壳里剩下的蛋清，我搂着娘嚎啕大哭。说什么也不肯吃鸡蛋了，爹知道原委后气得要用棍子打娘。最后我妥协，前提就是我们三人一块吃。虽然他们同意了，但每次也就象征性的用牙齿碰一下。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">村里的人从来不叫我名字，都叫我是瘸瞎子家的。爹娘一听到有人这样叫我必定会跟那人拼命。娘看不见就会拿了砖块乱砸，嘴上还骂着：你们这些杀千刀的，我们瘸瞎，我娃好好的，就不许你们这样叫唤。将来你们一个都不如我娃。那年中考，瘸瞎子家的考了全县第一的喜讯让爹娘着实风光了一把。镇上替我们家出了所有的学杂费，送我上学的那天爹第一次出了山。上车的那会，我眼泪扑剌剌的直掉，爹一手拄着拐一手替我擦泪：进了城要好好学，以后就在城里找工作娶媳妇。别人问起你爹娘你就说你是孤儿，没爹娘，不然别人会看不起你。特别是娶不上媳妇，人家会嫌弃你。误了你娶媳妇，我都无脸去见老祖。</span><span style=\"font-size:16px;font-family:黑体;color:#444444\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">爹！我让爹别再说了，这是什么话，还没有用呢咋就不认爹娘呢？娘也说这是真话，要听。你不记得在学校里吗？只要说你是瘸瞎子家的，别人就会拿白眼挤兑你。刚开始连老师都不喜欢你。以后，你带了城里媳妇回家就说俺们是你的堂叔和堂婶。娘说完就在那抹泪。爹说，不要把媳妇带回家，一带回来你娘忍不住就会露馅的。然后往我怀里揣了十个熟鸡蛋就拖着娘走了。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我的眼泪也扑剌剌地往下掉，残疾不是他们的错，那是老天对他们的不公。但他们却生了一个完美的天池给我。这个傻天池，这样的爹娘，无法再完美了。我很生气，他怎么就这么小看我呢？<br/> <br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">那后来，你就告诉你媳妇他们是你堂叔和堂婶？我敲过去这句话。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">本来我不信，媳妇找的是我又不是爹娘，为啥爹娘都不能认呢？不过我在外十年，爹娘一次都没去过我的学校。第一年工作，我想带他们进城玩玩，他们都不肯，说让人晓得我爹娘是残疾人会在我脸上抹黑，影响我娶媳妇，一辈子都在山里了不想出去了。娘还说她就是从城里来的，也没啥意思。后来，我谈了第一个女朋友，当我认为时机差不多的时候，就带她回了趟家。谁知到家后，她晚饭都没留下吃一顿就走了，我追出去她说，和这样的人过日子她一天都过不下去。还说我们家基因有问题，以后的小孩肯定也不会健康。我气得让她有多远滚多远。回到家，娘在那哭，爹也骂我。说我不听他们的话，非要断了咱家的香火不可。<br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">后来，我遇上了第二个女朋友，就是现在我的老婆。我很爱她，做梦都怕失去她，她们家又很有钱，亲戚都是些上等人家，有了前车之鉴我很害怕只能不孝了。但是一到逢年过节我就想他们，心里堵得慌，难受。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">那你从来就没有告诉过你老婆？也许她不计较这些呢？</span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我没说过，也不敢说。如果她同意了，我想我岳母也不会同意的。我和她们住在一起，岳父在外是有脸面的人。如果爹娘来了，不是在他们脸上抹黑吗？我也只能在出差学习的时候偷偷回去看上两眼。谢谢你听我说了这么多，现在我的心里舒服多了。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\"><img src=\"http://zhyuce.sanzhima.cn/uploads/images/20190514/1bd957d6965b7fb8c27ea8169b9b6ba5.jpg\" title=\"9d8f77c384ebff489a34df630d78edc.jpg\" alt=\"\"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/></span></p><p><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">&nbsp; &nbsp; 下了网，我依旧没有睡意。都说儿不嫌母丑，狗不嫌家贫，看看我们都做了什么？我理解天池的无奈，也了解他爹娘的苦衷。但他们不知道却将无辜的我陷入了无情无义的逆境之中。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp; &nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">天将放亮时，我敲开了部门经理的门，告诉他下面的事情请他全权处理，我有点非常重要的事情尽快要办，一切就拜托他了。然后简单收拾一下行李，我就直奔火车站。还好，赶得上头班列车。<br/> </span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">那条山路确实很难走。刚开始腿上还有点劲，后来脚上磨起了泡我就再也走不动了。正是中午时分，太阳又晒得厉害，我只有喘气的份。背来的水差不多快喝完了，我也不知道下面还有多少路程要走。脱下鞋子挤了水泡，那一会疼得我都哭出声来，真想打个电话让天池来接我回家，最后还是忍住了。从路边揪一把芦苇花垫在脚底，感觉脚上舒服多了。想到天池的爹娘此时还在家劳作着腿上忽的一下就来了劲，站起来继续往前走。</span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> <br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">当老村长把我领到天池家门口的时候，那一片烧得红红的晚霞正照在他们家门口的老枣树上。枣树下坐着堂叔，哦不、是天池的爹，爹比结婚时看到的老多了，手上剥着玉米，拐杖安静地倚在他那条残缺的腿上。娘跪在地上准备收晒好的玉米，手正一把一把地往里撸。这，宛如一幅画，而画中便是这世上最完美的爹娘。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我一步一步地往他们跟前走着，爹看到了我，手中的玉米掉在了地上，嘴巴张得老大，吃惊地问：你、你咋过来了？</span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> <br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">娘在一旁摸索着问：他爹，谁来啦？天、天池家的。啊！在、在哪？娘惊慌失措地找着我的方向。我弯腰放下行李，然后一把抓着她的手，对着他们，带着深深地痛、重重地跪了下去：爹！娘！我来接你们回家了！爹干咳了两下，泪无声地从爬满皱纹的脸上流出。俺就说，俺的娃没白养阿！娘把双手在自个身上来回的搓，然后一把抱住我，一行行的泪水从她空洞的眼里热热地流进我的脖子里。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我带爹娘走的时候村里是放了鞭炮的，我又为爹娘风光了一次。当天池打开门，看到一左一右站在我身边的爹和娘时吃惊不小，怔怔地愣在那，一语未发。<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">我说：天池，我是读你的人。我把咱爹娘接回来了。这么完美的爹娘，你怎么舍得把他们丢在山里？<br/> </span><span style=\"font-size:18px;font-family:宋体;color:#464646;background:white\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\">谢谢你！天池泣不成声，紧紧的抱住我，像他娘一样把一行泪水流进了我的脖子里。</span><span style=\"font-size:16px;font-family:黑体;color:#464646;background:white\"><br/> <br/> </span></p><p><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">做一个人需要感恩，一定要孝敬自己父母</span><span style=\"font-size:18px;font-family:宋体;color:darkgreen;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">。天池没有认他的父母，是因为父母嘱咐</span><span style=\"font-size:18px;font-family:宋体;color:darkgreen;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">他，怕他娶不到媳妇。孝顺的天池照做了，最后也和自己的妻子一起与父母相</span><span style=\"font-size:18px;font-family:宋体;color:darkgreen;background:white\">&nbsp;</span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">认，孝感动天。</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父亲种的花草树木已渐渐荒废时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现母亲煮的菜太咸太难吃时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父母经常忘记关煤气时。</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父母的一些习惯不再是习惯时，就像他们不再想天天洗澡时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父母不再爱吃青脆的蔬菜水果时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父母爱吃煮得烂烂的菜时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果有一天，你发现父母喜欢每天吃稀饭时；</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">如果真有那么一天，我要告诉你，父母亲真的老了。</span><span style=\"font-size:18px;font-family:黑体;color:#464646;background:white\"><br/> </span><span style=\"font-size:18px;font-family:黑体;color:darkgreen;background:white\">要记得常回家看看他们，不要让他们觉得被遗弃了，因为孝顺很重要。</span></p><p><br/></p>");
            getInfo();
        }
        request(1);
    }

    /**
     * 获取info
     */
    private void getInfo() {
        OkHttpUtils.post()
                .url(AppConfig.getInstance().COUNTRY_DETAIL)
                .addParams("uid", SharedPreferencesUtils.getUid(HomeArticleDetailActivity.this))
                .addParams("nid", nid)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ArticleDetailResponse resp = JsonUtils.parseByGson(response, ArticleDetailResponse.class);
                        if (resp != null) {
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                //请求成功
                                ArticleDetailResponse.DataBean data = resp.getData();
                                articleTitle.setText(data.getTitle());
                                Glide.with(HomeArticleDetailActivity.this).load(data.getImg()).into(logo);
                                subname.setText(data.getNickname()+" "+data.getAddtime());
                                address.setText(data.getAddress());
                                WebViewUtils.setH5Data(webView,data.getContent());
                                sayNum.setText(data.getDiscuss_num()+"");
                                likeNum.setText(data.getSee_num()+"");
                                all.setText("全部评论("+data.getDiscuss_num()+")");
                            } else {
                                //请求失败
                                showToast(resp.getMsg());
                            }
                        } else {
                            showToast("获取新闻详情失败");
                        }
                    }
                });
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_gray_back);
        title.setText(tname==null?"":tname);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_country_share);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightWithIcon.setVisibility(View.VISIBLE);
        rightWithIcon.setCompoundDrawables(null, null, drawable, null);
        rightWithIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
    }

    /**
     * 创建表头
     *
     * @return
     */
    private View createHeaderView() {
        headerView = View.inflate(HomeArticleDetailActivity.this, R.layout.header_article_detail, null);
        articleTitle = headerView.findViewById(R.id.articleTitle);
        logo = headerView.findViewById(R.id.logo);
        subname = headerView.findViewById(R.id.subname);
        address = headerView.findViewById(R.id.address);
        webView = headerView.findViewById(R.id.webView);
        sayNum = headerView.findViewById(R.id.sayNum);
        likeNum = headerView.findViewById(R.id.likeNum);
        all = headerView.findViewById(R.id.all);
        likeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞
            }
        });
        return headerView;
    }

    @OnClick({ R.id.backLayout, R.id.sendButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                finish();
                break;
            case R.id.sendButton:
                //评论
                break;
        }
    }

    private void showShareDialog() {
        ShareDialog dialog = new ShareDialog(this, R.style.BottomDialogStyle);
        dialog.setListener(new ShareDialog.OnDialogClickListener() {
            @Override
            public void qq() {
                //qq分享
            }

            @Override
            public void wx() {
                //微信分享
            }

            @Override
            public void wxCircle() {
                //微信朋友圈分享
            }
        });
        dialog.showDialog();
    }

    @Override
    public View createTitleView() {
        return null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_article_detail;
    }

    @Override
    public void request(int page) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("数据" + i);
        }
        manager.setData(data);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.releaseRefresh();
            }
        }, 2000);
    }

    @Override
    public void fillView(BaseViewHolder helper, String s) {
        CircleImageView logo = helper.getView(R.id.logo);
        TextView name = helper.getView(R.id.name);
        TextView content = helper.getView(R.id.content);
        RecyclerView applyRecyclerView = helper.getView(R.id.applyRecyclerView);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ApplyAdapter adapter = new ApplyAdapter(R.layout.item_article_detail_child);
        applyRecyclerView.setLayoutManager(new LinearLayoutManager(HomeArticleDetailActivity.this));
        applyRecyclerView.setAdapter(adapter);
        adapter.setNewData(list);
        TextView time = helper.getView(R.id.time);
        TextView sayNum = helper.getView(R.id.sayNum);
        TextView likeNum = helper.getView(R.id.likeNum);
        sayNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回复
            }
        });
        likeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点赞
            }
        });
        likeNum.setSelected(helper.getAdapterPosition()%2==0);
    }

    public class ApplyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ApplyAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
//            helper.setText(R.id.childName, "");
//            helper.setText(R.id.childContent, "");
        }
    }

    @Override
    public void fillMuteView(BaseViewHolder helper, String s) {

    }

    @Override
    public void attachActivity(AppCompatActivity activity) {
    }

    @Override
    public void dettachActivity() {
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public boolean isMuteAdapter() {
        return false;
    }

    @Override
    public int[] getMuteTypes() {
        return new int[0];
    }

    @Override
    public int[] getMuteLayouts() {
        return new int[0];
    }
}
