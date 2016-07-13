package cn.InstFS.wkr.NetworkMining.Miner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import WaveletUtil.Mallat;
import WaveletUtil.Wavelet;
import cn.InstFS.wkr.NetworkMining.DataInputs.DataItems;
import cn.InstFS.wkr.NetworkMining.TaskConfigure.TaskElement;

public class WaveletOutlierDetection{
	
	private DataItems outlies;
	private TaskElement task;
	private DataItems di;
	
	public WaveletOutlierDetection(TaskElement task,DataItems di){
		this.task=task;
		this.di=di;
	}
	public WaveletOutlierDetection(){}
	
	
	public void TimeSeriesAnalysis() {
		// TODO Auto-generated method stub
		
	}
	
	public DataItems getPredictItems() {
		Wavelet wavelet=Wavelet.WaveletFromString(Wavelet.Wavelet_db1.toString());
		int layer=2;
		Mallat mallat=new Mallat(wavelet, layer, di);
		mallat.waveletTrasfer();
		mallat.softDenoising();
		mallat.waveletInverseTransfer();
		double[] CA=mallat.getCA();
		int row=1;
		for(double value:CA){
			System.out.println(row+":"+value);
			row++;
		}
//		DataItems dataItems=new DataItems();
//		for(int k=0;k<di.getData().size()/100;k++){
//			List<String> list=new ArrayList<String>();
//			for(int j=0;j<100;j++){
//				list.add(di.getData().get(k*100+j));
//			}
//			dataItems.setData(list);
//			Mallat mallat=new Mallat(wavelet, layer, dataItems);
//			mallat.waveletTrasfer();
//			List<double[]>CDS=mallat.getCDs();
//			double[] s=new double[3];
//			double[] n=new double[3];
//			for(int i=0;i<3;i++){
//				s[i]=(100*Math.pow(Math.log(2)/Math.log(10), 2))/Math.pow(2, i+2);
//				n[i]=getN(CDS.get(i),i+1,100);
//			}
//			double hurst=getHurst(n, s);
//			System.out.print(hurst+" ");
//		}
		return null;
	}
	
	
//	private List<Integer> checkOutliesByGuass(double[] values){
//		DescriptiveStatistics statistics=new DescriptiveStatistics();
//		ArrayList<Integer> outliesIndex=new ArrayList<Integer>();
//		for(double value:values){
//			statistics.addValue(value);
//		}
//		double mean=statistics.getMean();
//		double std=statistics.getStandardDeviation();
//		
//		for(int i=0;i<values.length;i++){
//			if(Math.abs((values[i]-mean)/std)>2.5){
//				outliesIndex.add(i);
//			}
//		}
//		return outliesIndex;
//	}
	
	
	
	
	
	
	public DataItems getOutlies() {
		return outlies;
	}
	public void setOutlies(DataItems outlies) {
		this.outlies = outlies;
	}
	public TaskElement getTask() {
		return task;
	}
	public void setTask(TaskElement task) {
		this.task = task;
	}
	public DataItems getDi() {
		return di;
	}
	public void setDi(DataItems di) {
		this.di = di;
	}
	
//	private double getHurst(double[] n,double[] s){
//		int length=n.length;
//		double jsn=0.0,js=0,sn=0,j=0.0,j2s=0.0,js2=0.0;
//		for(int i=1;i<=length;i++){
//			jsn+=(i*s[i-1]*n[i-1]);
//			js+=(i*s[i-1]);
//			sn+=(s[i-1]*n[i-1]);
//			j+=(s[i-1]);
//			j2s+=(Math.pow(i, 2)*s[i-1]);
//			js2+=Math.pow(i*s[i-1], 2);
//		}
//		
//		return ((jsn-js*sn)/(j*j2s-js2)+1)*0.5;
//	}
//	
//	private double getN(double[] Djk,int j,int N){
//		int n=(int)(N/Math.pow(2, j));
//		int len=Djk.length;
//		double total=0.0;
//		for(int k=0;k<len;k++){
//			total+=Math.pow(Math.abs(Djk[k]),2);
//		}
//		return Math.log((total/n))/Math.log(2);
//	}
	
	public static void main(String[] args){
		DataItems di=new DataItems();
		String str = "458.024049071502	456.803987512162	455.089266656752	456.849684728262	456.191869377354	456.606837292893	455.417575231457	457.110301169050	457.059556088102	455.605506710199	452.679260192480	450.552652150749	448.474840688580	450.965456825316	450.651602381406	453.373164774294	448.844790841296	449.536568609404	448.829857617536	445.946097284398	445.662794755407	443.515755864990	446.040447220410	444.695174529692	445.537777744899	445.183696256345	446.056608139982	439.282229256118	439.981910600417	442.666488076526	440.933919987315	444.945238115257	440.731841159176	439.063030528919	440.809333355439	438.972644162178	437.823915155044	438.756806884130	438.400504957792	436.180262361300	435.127217908260	437.814709470321	437.482744027992	436.136668411474	432.877567481473	431.550151951271	432.471761151353	432.502422069119	432.671747709074	433.678283840446	430.441064166081	426.250400149396	428.792961892413	430.940190141739	427.672443827339	427.426532460094	430.061240810274	429.796269758519	424.860501453031	422.030942178605	421.778880059124	425.822912164494	424.743090079895	422.492834102626	423.544611269835	421.488554839331	424.161711639129	422.870645128479	424.546723348322	422.706375349918	420.737751343033	420.240882813504	418.927802563874	419.802343864397	420.466908121139	417.738119979923	419.065032024480	419.934557575562	417.781112612712	416.162760754142	418.768174816202	413.057672214073	413.192394453454	409.429785504162	408.571650044226	409.403436641520	407.575823326468	408.870757608889	407.852333056070	402.036290167526	402.558101071669	400.782168384500	399.099942292570	401.437448536680	392.937189949109	388.680370888655	402.076196466938	401.481400799046	403.199651265107	396.923600862190	397.931845087394	385.515213944613	385.055318729843	401.779650789439	397.870190974711	394.751875575480	395.757498321655	396.783241098050	400.726107067562	390.716044207303	387.378554744627	399.418083837530	395.124595185740	391.339843719387	386.256517731188	381.468928648591	395.132210391699	392.802418520989	391.769916305683	383.979306397075	390.801928044079	392.932627867667	391.639717637870	380.399690145545	379.491632530630	385.271867854072	383.292057026236	389.483691741566	389.835659086952	387.608495529865	388.926698247933	388.288184667380	388.993217205627	390.788908498690	389.221438509163	386.060102028383	355.120828892951	373.589684648024	375.305354643216	380.842113719671	382.819267763841	378.451952438912	373.230591600205	379.082717944678	358.061051537494	369.056168491604	379.612573466882	376.497770109216	372.947148125923	361.616744823115	373.575625212872	366.198805234912	378.877054134619	373.851411513247	374.914922832365	372.630891898364	377.186748468973	354.173032713870	366.429111271501	370.606160842387	373.712902634229	380.817693874384	377.187666220158	372.184872190380	372.240461070866	369.095647380342	379.179598151040	376.175599538255	377.849463285150	376.346554671226	377.693380533903	363.848815439390	355.891448990313	373.653444991037	371.637108190125	371.337232548078	377.753227488604	379.122531763757	376.266416730710	370.398600533205	367.577283246424	364.805173383690	373.839232374162	360.805645490111	361.776247304958	364.491913880515	368.394175379334	371.353875994773	358.930954788969	363.515922888178	366.199939365758	357.276930170814	356.600749944789	356.916004675026	367.862693756520	364.387231726439	355.022850761308	373.030429955183	365.794121217621	373.790044665435	375.146118265076	365.106880177387	367.827532290076	369.466551147622	374.962577481754	361.879591854395	368.758713122967	361.635498333023	367.969775134250	369.432735764896	374.679544820172	360.851721326052	368.766933380641	358.236005138629	366.042605861656	368.998135506036	360.093808769234	369.973730944028	364.439320416710	359.751457249464	370.567709666357	362.250275477947	371.089407323519	374.859809163679	369.457791729318	371.680879625954	372.275591460228	350.420381976425	361.144323470604	362.555376594657	369.209699209747	362.258492566830	371.047419725447	372.830288192764	357.231333801753	362.302308081769	369.759663764200	360.949617585058	371.716660572714	369.256628414974	376.211690457300	358.598206356495	376.969605987345	376.769799474479	378.068234043999	366.035164433383	376.256951102195	367.074267105280	366.425906297374	377.420754774254	377.089205592063	371.868044012731	371.505618958208	371.000341393895	370.542425551543	363.406651082811	368.659563240671	374.571223004010	366.799314267601	381.625856870257	379.896346653780	380.974058438806	373.433292704629	367.423146912640	370.365798365814	360.969345876553	380.845073627846	372.216973177283	371.922171037378	363.888312815340	364.760747743353	370.514232721603	369.762745490547	375.161765174214	365.234213411138	366.053696436804	373.391942021089	368.194548326385	350.091868714694	361.894363186790	346.250848066647	361.862205252505	366.610703601870	356.364313420901	367.880203547940	354.602637409844	363.198754292229	371.151210141899	367.787893275878	365.219793457930	350.602382033091	368.791412765444	354.544956554005	354.923563668475	367.278782817084	366.332722608404	364.369631653167	351.647702120625	362.755026276126	358.103080011677	363.094891057770	331.554324686405	352.979601957402	332.906688918374	337.294121310740	337.947630823560	349.909175860586	346.019190078284	337.726549712309	344.488329892248	339.241061635236	342.098381193674	334.520802415052	339.967746754250	338.450688855249	344.113197231540	343.198520597308	331.583479872382	337.322361763221	337.162512156241	343.328090017649	345.171123861360	330.983458015023	338.451292174160	330.544828987066	324.822185247414	330.329048711096	338.203385646388	335.833886602422	343.752825863594	337.377056453344	334.800276238313	336.612750067325	333.478028382967	337.427183128173	330.166223853541	331.746466316812	338.733126437093	331.089488592192	314.705243252237	324.901149521710	328.732811539165	332.421997050361	334.433232677770	322.115569198621	331.838685214916	310.953452628382	330.561040328789	326.177846766652	321.696700584780	320.874423193104	321.281996186878	311.764578328387	317.835985924591	328.414152819284	321.572074330311	306.936155230526	316.021307615627	314.219254271444	318.049583892127	322.003574143381	327.119490462179	317.858480277984	307.686333860476	299.670922795378	328.058075828194	314.061044049088	318.735079266186	310.853427112926	304.991601505177	321.876367528030	311.928686641765	294.707545208778	304.865322891960	294.872262693721	299.919460857885	314.965802847606	315.591770645761	302.617654898062	306.385666841551	297.684579829981	301.041920231373	292.535155702639	306.518259043775	294.854281324480	310.414346736807	305.898369504176	308.423438343117	311.108482640453	312.875374409599	305.715856706452	308.647697608032	297.648305859079	305.238106124770	295.447994648105	284.803690957712	296.834960748017	310.305895618014	309.397968444566	311.226379890353	296.911764098486	296.380886960584	304.082381560154	305.444683426250	303.367649403266	310.755735345741	303.982252596539	299.211846939401	304.327801669071	295.411597914136	304.106633326264	306.906248008428	312.739962717046	294.493373006862	304.112121108997	294.677879540114	293.401054620153	310.365957214738	310.025997125436	308.655333708694	307.306979521151	305.477622213859	301.259994710235	305.001710860536	299.967595037566	309.508844641301	308.104581742635	306.099930420296	302.080745626375	302.974536295342	310.168893950985	313.815019543622	308.740510877782	313.960105570632	315.214409074632	308.902760497188	285.895650011800	309.800580028553	304.127119876892	296.921302923032	313.962386266011	303.678048835694	305.239412526127	298.380566303137	302.211987082233	300.104761679837	325.347847062716	322.838287642145	325.126163866814	317.490738613828	304.922802953629	308.807278715257	304.404076668321	316.750916715034	314.533640460244	302.262896467485	320.880294491130	313.996864377630	313.622828506798	317.960004503368	299.768002367884	308.895337139017	313.853133377278	308.208858376600	316.192319464858	310.661582461474	306.739955817240	318.107629334991	320.847529672774	330.561073441458	327.630743776877	318.146710806014	332.989290176497	340.662221215839	353.039039617098	368.281939056129	368.153934969918	364.211704295252	370.215604740471	379.388876879484	381.767537665542	396.336028154552	373.497124585257	381.919211210616	381.021886673422	394.212750531313	396.404462146880	393.695395436152	393.799052460148	393.161438253275	387.989967896504	378.144371808415	382.920748987734	399.809270007622	379.957857644179	386.689541552029	386.629537525761	382.843330432068	380.511091253545	375.681539376754	379.162027676595	386.571233052361	382.058410616307	403.996316477693	389.440441628557	397.586188134083	410.440140809980	413.180061721425	409.130794167404	393.192588584539	410.766357199907	407.182208997050	413.449649130774	412.162022437850	402.238234484965	411.975676332954	400.367372778501	405.443820064431	395.101780185710	389.129609239661	413.059776651756	404.675738606584	405.825707961758	406.299387625777	395.489617050966	395.212234182192	384.030314150434	391.476074148999	381.509139205421	395.625768735922	400.901185361392	382.069145303242	387.922526826232	406.014355500556	424.640110891298	431.588562827726	434.665723140364	435.197326161985	437.229698474714	416.644165027161	408.755282862678	415.174474135436	421.648433170658	412.712078021773	396.318330616066	380.189845177822	369.984459565503	361.619483129878	361.381511899170	360.378025942547	363.007798640845	365.297795897617	370.423325887504	378.643170601205	384.055324845524	391.105390356628	398.344249837239	403.046965660434	407.235839102417	407.225029017584	404.352512173622	403.378196509099	410.474195448268	398.396659704607	414.314338545193	407.768503599049	409.653797779378	390.644024195598	392.035053018922	412.074599181202	415.555608865182	412.303481375267	401.919171326801	400.439620999443	400.976898647630	403.538774653210	390.129506205017	391.448409698997	391.819753800057	401.383735447386	388.005326681635	380.010984976248	378.194886642756	369.729490963420	378.440729945968	383.662200770818	375.699144527432	378.269785962479	375.454347754345	362.765988472529	371.814955225011	367.216864625575	374.244438191991	359.397620957962	353.864585469907	363.133658460152	347.848565913281	350.951986543437	355.880909095291	349.754487730361	349.492669880706	341.910747853482	342.552481756408	336.494027861863	339.977536207445	336.079504169927	327.511988993673	324.696507171687	336.421262185939	332.809029545449	332.737529271703	320.746092865501	327.052567353274	309.251335511721	317.258710747992	309.418531321139	310.945607099203	311.571077560887	314.881528476236	319.538888259097	325.590844793900	301.886708844567	302.249099031166	298.147213270215	303.701762796612	323.337329837232	311.692148852687	312.155484572040	307.543093796161	315.615765060588	305.780124551508	318.396453934279	302.199070780045	321.263250592470	299.301382498424	309.095688644323	304.944760081978	320.216151801511	316.887097203072	318.295205374558	312.087060879239	306.121801350942	317.357733938547	293.304377961682	298.033073134655	291.945509600702	282.423315515568	292.525026978310	291.935280163596	275.581917986882	286.013597055853	289.707565346960	294.700864646461	293.991109167164	295.385009719305	307.884096211273	294.586090570391	294.203594990818	295.033941978209	304.067290246180	314.051282787195	312.885288879153	312.647507530325	308.849688696684	305.896017625898	308.781002393021	296.848914578569	312.434678720299	305.021929664358	308.883890350414	293.767470107753	299.017499915258	301.751560917257	279.743464637820	284.101696530511	291.855534167562	288.185623039219	280.927286324570	287.505949258238	266.895073109623	273.972475007942	259.979577320957	270.351180876612	281.043878652675	270.127265003290	273.531595603341	276.887629988854	267.840490063728	266.552998997613	282.859673585977	287.420104309964	284.928624961757	280.560833986715	284.269247040269	272.300902857348	267.835439676856	265.288779538309	267.608698590466	259.594365095355	249.685574025723	250.218521358632	243.797406017358	243.663155448382	247.445945612121	230.674984083228	227.841302534373	246.395997153331	255.361974628165	258.755255732442	242.123595868251	245.162512734062	239.582071323480	233.612465091607	222.260753143059	239.957804864566	239.674117288474	239.145235815071	244.434426116493	224.400154565118	236.240420417424	232.113681225809	218.783426594550	218.028416293936	216.108256509483	234.738148727636	235.895461983547	231.334436478601	231.521408605429	228.326633120748	224.302501812449	228.547527877426	225.690817638172	222.708183034808	227.251918776297	224.974341678220	203.595343551899	205.556369968781	216.025112202296	208.800185157640	210.675773883269	206.764397775256	211.026032881569	208.857082047877	207.765249498903	208.638844507598	190.974924915227	193.013105397376	203.304083546168	195.699406188950	192.594521349408	187.080028900022	199.133608856762	187.083189208228	181.877312886446	185.667187144812	180.989245900684	188.661163023083	178.300559529042	181.507915708763	192.162668997719	179.261135327480	189.845214498440	178.740998898647	191.406423202741	173.712404394668	180.826448142515	185.204284727004	173.749066678131	172.767977260709	167.743854530688	180.578277368244	170.913217420561	176.670275450521	178.140924266003	167.445716717507	172.765620358666	170.883474126384	163.969389917179	161.239602784941	158.563526134791	155.132311555911	160.865333193283	170.778028517754	158.204001284767	166.384038669326	165.048760405718	151.262012596963	166.835936891197	154.174058078681	161.739153995822	148.531506090169	168.615637091344	158.130196154678	160.662383540130	157.411504237721	162.403897302763	155.270855967335	164.424377144133	163.174185895715	175.403919321890	161.570688999778	148.730006825293	153.779551896698	156.538889742492	150.491524537625	157.312915576761	156.000138426951	156.590613101534	143.235591273703	146.257603120638	146.913723404107	146.856135731569	156.780614284450	149.934057512160	154.840438503661	155.292220197674	159.082582584711	146.841184298116	154.004706569772	159.546657571040	142.792495070415	149.503542326290	159.444250434816	145.522389308749	150.978261888880	154.664514403118	148.639799951346	161.528472779461	155.149556640777	140.140296637399	147.885271419273	154.473057209471	144.798355423932	165.936525425914	146.184156975677	147.832492272715	150.601838472554	138.338645762174	136.353480727038	135.592868359314	152.572427084826	136.318941128398	138.453812701679	147.969796597419	143.171726095849	143.340392018593	145.374339385597	135.764108071489	135.327113490103	133.870863327555	126.683264782676	149.256484951129	139.553876118452	148.063078463790	142.309890052332	126.134418776064	131.869608760748	144.605697817610	128.174181887580	132.534830478017	138.776425951558	145.700447546728	125.280097836069	140.234670021079	129.599831300120	136.367263573938	146.453250695868	141.316490720693	149.179639903499	144.509505534591	149.267313538213	133.192029547657	137.740165709801	134.023690441615	134.482821815946	155.659190511369	149.447993358720	134.334495710671	141.328283764733	142.101018205604	149.752852362561	144.952174157091	143.706327503065	139.289972580946	138.502453105178	146.815288236314	135.607092714210	152.575661493633	138.052147825726	147.922389591195	132.504425025866	144.145571920061	130.325149884333	152.539295491974	131.828331619829	142.096421906463	151.196066665995	134.225464664554	141.165079222588	127.862662279667	133.214440377365	125.753008314326	135.667780853273	130.118504928120	144.731423549062	130.886041457995	141.064764907263	140.485684225250	125.181959859724	128.233518094356	135.246961342290	139.284506814458	121.786225235685	134.933739841962	125.341263795313	130.240736670861	141.643284970048	131.646773658738	134.399609001284	137.054852881539	125.695528963296	127.606542950425	131.242584587929	139.907246801062	144.210635731235	131.955873272458	127.839617837275	135.242647909397	127.907591993109	142.840686673138	134.779972417738	143.583247795771	129.248633745754	127.501158271754	129.731820997274	130.596999824560	130.284872606045	137.125541228772	140.136196417539	139.679554799261	136.869935610463	138.291959931685	141.286919769369	130.846470036477	149.398829131257	140.113380258876	135.312711144039	137.682932647264	137.542120262973	143.436170605898	146.259781654381	156.000708506497	144.853922480062	147.739427504112	149.315286250487	139.020137393555	162.732246591319	150.828321700053	152.085703154801	157.033009086315	159.485190901657	140.713608424733	154.884563379226	143.018771899003	156.540395698024	165.111039088432	147.695030241157	151.981836861363	152.525986353131	150.278270309465	156.592009705460	166.512042245061	162.912898961679	175.595095103827	158.921926567948	160.767691332744	168.598905458197	158.897161179098	165.390812454255	175.570790645842	166.801952523422	162.040276879225	171.771175334220	167.546140046189	165.116863710539	185.381032681956	178.090723666844	170.345792737381	165.813684216431	175.808994265050	170.108278161688	177.056352494779	187.200465905595	194.254180882505	182.556980009608	184.124394791527	194.072098599770	188.931427218168	177.734907544776	183.742118841719	206.409757325702	186.352454024412	191.398473829368	189.827403281599	190.795746054986	200.464298675253	189.279603259561	208.701221702893	200.247287444858	206.332763133868	198.236954032878	215.346498996927	217.166048714707	197.663038888879	189.655581889423	192.023288223289	188.782936364127	192.035202604922	192.410887256496	204.315759196659	200.927793938303	214.158488761681	196.603605708178	198.829164394058	199.204695776084	204.650226420387	203.274194931433	212.372823906314	217.349024637456	203.716642625956	216.138550805087	215.946859047467	206.174419034467	215.769857211658	217.707335253745	234.316740431421	235.269949290583	222.287382667185	234.765691509397	225.590019345762	231.180266836813	245.580385038739	230.405463751865	224.511081783425	223.826415317078	223.892397183751	233.318411102877	237.652987199889	227.457452911480	233.293004503601	225.192877072916	227.141540294208	229.030406630318	235.123121337696	232.650671498068	242.585804289545	243.608519001579	233.275087203624	250.189254709090	236.650318649065	245.940694375465	237.764191622189	256.647260680577	244.183911657499	246.190066833708	240.776713348121	240.743286835588	240.862525649020	247.132235180719	266.436210195571	263.241563861702	245.877075711455	249.316697918010	253.639075201673	248.263586250479	253.885725878852	269.509278990771	260.428303431925	252.596339151813	271.022713573379	256.139449121441	256.800353583388	273.005624425201	284.370733643652	274.406478030050	260.683571064505	272.960294972249	261.155427224313	261.713984355406	278.840020376999	275.146849624261	275.338279921480	263.441584431880	263.922550968925	265.718012741040	277.272299356150	271.057848686972	271.740806043884	270.514574686783	278.297921833883	268.481798420969	274.758343724640	273.209206696386	282.316345992489	306.410370801574	266.813592214628	247.578190767978	284.294138300294	312.133071169459	320.668654849201	319.517996444475	321.202995908183	323.809698712451	324.823351281305	325.929068957024	318.769029223518	319.105958992423	319.292703182990	318.810943846010	319.323864256839	317.655860425965	323.787424155955	324.217740289821	318.643031186193	318.517729555552	321.770367576346	321.532309139563	322.175388055780	321.579319480456	324.887612064487	324.684207058388	324.662240263970	328.889485113301	327.413704557540	324.202316489823	325.799053880018	328.193694498456	325.496502055482	326.572444658428	322.855904903019	327.198015839460	328.569280103180	329.524143737925	328.288728366917	328.157696941994	326.871610385026	329.610124516000	329.210376398193	331.355636231532	328.732828755031	329.524339626755	331.634838847798	331.548179145388	331.178476770161	328.810379840251	326.334440069530	330.036181745552	328.755416790907	329.265143428009	327.323198862180	326.994845870310	326.025682490238	324.198627554221	328.307210108742	328.484853106181	328.817752942967	325.563108635465	325.443602202330	327.555067095582	326.541611638618	325.562838013029	325.495416339726	325.729849117675	328.247705679163	328.945956150291	330.533204081132	330.615710128626	332.102152548956	330.866537095443	329.827037776279	331.184518462541	332.803156060756	328.947492790995	332.175842312700	332.415595254529	330.616334843168	331.652359081201	328.913650710540	330.105102988853	330.240021110270	331.227157382709	331.207881902644	331.987623293836	330.783790837991	329.391230092005	330.326113345958	329.848497320921	328.078061153547	330.265586525963	327.414781792057	324.915509978736	325.959706784963	330.921973579883	328.697692164119	330.612816820362	331.994730704706	327.499180367060	328.015494621446	328.321807952916	326.677845267858	329.091509583699	329.668505622328	328.660837455655	327.873244827974	328.618942394988	327.486329778859	331.178965607282	329.675285042474	329.757178611704	332.811709720478	330.887529288166	329.843371222469	328.505677497811	333.287344998195	333.052962442140	333.538764855941	331.324758942176	331.826838765853	329.782310255197	326.859104217759	324.574005392404	327.092042109848	326.130137465602	325.621807981972	323.717488310572	320.653593726919	325.512382925352	326.835522898327	324.856922210648	324.545550312134	325.195820529385	328.787874767877	328.771008595156	326.459357583782	325.463743591901	322.028595629064	323.105265215385	324.653804584527	323.347853143281	327.460488591783	327.894804928565	326.927485379021	327.260232333217	328.043972513832	325.314629200163	326.496415964609	325.435332466788	324.008926140069	324.193004148068	321.104715035378	321.156166714628	323.226685427333	321.430981413445	327.984411297016	324.664526752197	325.903596238466	326.572735574024	323.917278633813	326.324422816556	324.414482404653	325.411187412624	326.246610470797	324.946272063215	326.602215054720	327.515506491847	327.963902482754	325.882857515618	323.872276085159	328.691664518062	329.592038090001	324.852810109459	328.505128420848	325.466964840224	324.979100553568	325.806744122321	325.417666163946	325.005733581964	322.488414441710	324.643096016172	325.120264914210	328.140065057218	322.970617919770	319.865924891343	320.201429127727	320.195745203206	319.666089417177	319.618310761612	322.062705425450	321.279147033223	316.700109576146	314.497265300509	314.501207955014	315.093763072116	313.565983414325	313.508603537003	312.232106681551	309.862780744175	312.725282863239	312.210412564995	315.820148522943	316.980664136542	317.332685228682	321.552195407163	323.276544438616	327.584685653859	333.274763059779	335.110425978076	338.877500963854	339.853167981364	343.637010756548	342.249554207129	344.693132926463	343.448295966916	342.659264599500	342.490412855796	337.957629937368	337.957671142669	335.411989492940	334.057650099410	333.055344847996	334.466091315584	332.742893384626	330.800938708685	328.834751329282	327.747821340051	325.341811574753	320.810095441007	321.730489260462	322.788701089998	320.955193518875	317.776906929298	317.960883699214	318.774535596286	320.862390033350	318.809807133253	322.796371920920	324.117336036616	321.899894828966	321.845131697567	322.144210004854	321.783309317496	321.676176242534	320.321002709738	318.321550726220	315.714848820021	314.352633732641	314.730935479041	313.538352743073	314.284859646469	313.424717204420	310.685033551005	314.204132252421	312.289107444566	309.817788372431	306.115671071575	307.872682947156	303.276159656611	308.168548314784	307.779729334783	307.257379001230	304.555984492748	303.654389156733	304.082852903677	302.034834210176	302.515592188613	302.802400311948	305.042164508087	303.744951625172	298.087953068778	297.718601908608	300.105270925285	300.183182729792	297.202621054857	297.451067061680	298.863726630204	294.880980567437	292.222818544415	291.792400607000	290.512802480091	288.574650394986	289.340831893387	288.048761690620	287.810766766699	291.442384060849	291.344859971509	292.097128965220	287.719578062058	284.988823601228	284.161780145985	285.790664254756	285.770912752753	281.228587871815	282.404091676329	283.343137669334	284.827368462183	283.100407841978	283.712777521928	285.239460838797	281.938099002256	278.527115309084	277.579153469305	279.587764798347	282.047893332595	281.904029669366	278.590034905024	278.549956239135	277.440594219782	277.909516291242	277.101306458999	277.768923735573	273.889371079660	275.509838835659	278.836947768586	276.426976330198	277.246419820629	279.553539891458	276.217324845555	277.822700191768	275.462948409145	275.362895797237	276.703807780882	275.553265331096	273.743222290568	270.065046404856	268.589465726094	262.144120352374	263.245449137976	262.276568853077	262.240208500195	262.752942974385	261.073761766262	260.910299695772	256.121076816524	257.213226781905	256.839346116382	255.666284905281	256.898390946851	252.151980391165	253.740900173699	251.201334066182	252.536968511103	248.227893715858	250.541191839254	250.957504777002	251.286062297835	251.935926711427	253.743697177425	250.966265694624	249.914773060937	252.964762673816	249.233424901674	246.404328280999	244.443677846590	245.670198000229	243.363884076460	243.251609886879	241.359119582003	238.833839667707	238.229983673612	237.429364808401	239.005773892822	234.267912549901	234.758147920668	235.363785272299	234.644865822976	235.640247666076	234.265701863700	236.019525621441	232.202274366128	229.427916701090	231.779324292837	231.553975263282	230.512171046907	229.858024602517	228.807647291350	225.844613232986	228.441546544793	227.341512536529	227.132352914559	224.560304416927	223.065071269052	226.482109560735	226.179352071924	226.294029654581	223.629606784661	223.274364864117	222.963308603116	223.357957480332	221.396781558739	222.041054047158	223.292892484456	220.631593694679	221.585349732384	221.769819099760	221.490797601172	218.492180656202	213.698083426466	215.698983193867	218.904841074107	216.883976961787	215.344200349947	215.398616476375	216.519277160953	216.233362767504	217.520774258349	215.420479950221	212.433189649761	209.860759772674	211.329697415417	213.639692880288	210.578826250539	210.029090581178	208.200840109329	212.002104316796	209.864712703715	207.697250218408	206.579308999227	206.610573619159	207.714213372780	207.433818109193	207.840675245569	206.138183103377	206.216528716368	209.788518035656	208.939933551266	210.192947742417	207.816882917782	206.783985150017	207.624546397450	208.122096464889	211.894669088140	209.990248444554	207.309779911557	205.929217491677	203.277457212730	204.919592971669	203.837927677650	201.567346853532	202.535479818686	203.331225294951	198.343984621639	199.659571968201	200.180660814071	199.041759229068	200.650006202567	197.164482652105	199.968680466455	197.886234942021	198.543194704276	195.317057360928	201.636582678250	196.384352745881	196.007412380231	200.143895570442	197.006322168685	192.930104391972	195.772204035558	196.499538657638	197.002961729288	199.360263609747	200.339872081634	199.612205818665	196.156703657786	197.943401269726	198.388907488258	199.102567510162	197.096036590822	196.965269937784	198.227026537801	197.303769404929	199.553671005545	197.516885094055	196.696372999844	195.969143413304	197.563251146498	200.571820197411	195.037703211309	195.944975848907	194.711306547649	195.977431266015	195.249351046584	197.275252098273	194.106314153116	197.287375797946	197.751225185279	191.521199421355	195.928470079326	198.147107427572	199.406222258971	199.956796794122	201.711705320199	203.163262286171	199.696779764405	201.088447307896	198.118832290012	199.407091152830	198.553353349069	199.980366524712	198.901282123192	195.776179489803	195.834493653879	199.342156813656	198.218833123536	196.615880577727	200.157161692591	197.811736713975	199.243267382107	201.883986372002	199.835424053293	196.146170449175	199.400382160621	198.912366042057	200.852060210798	198.163106637330	195.618757139596	194.636624004595	195.978636388260	197.454202537849	194.726678014774	195.082404905445	195.847583482073	196.094139454946	195.598631229751	194.232822871610	195.384796080677	194.676398251722	196.740065637063	195.762402232744	195.252748922704	194.901144120213	194.747901795631	194.636707321773	193.277911034349	197.961218152016	196.870865773173	198.431301518567	197.600403507081	196.049308864077	196.561771412663	198.153202350887	197.154634545677	201.997875613621	197.210931402653	199.676573875251	201.698359617543	199.291192873483	198.093075788773	198.080858068944	202.330641757746	202.192524055013	202.607880912941	201.340537801185	204.024603502645	203.115279877776	203.418078216688	203.980135357584	202.602859242822	206.082126800547	205.527905177388	207.982888771969	208.079745518648	209.826356355043	210.582601971902	211.167189960951	212.909597980281	209.973323675028	210.797177088462	211.414121905040	211.618817082191	216.273134909016	211.452708355292	212.790212707859	212.001893375737	216.072549578023	214.584040846040	215.560376889547	216.741968491047	214.016036022451	216.247917129800	217.033695293108	218.108543692981	212.996636802312	214.640095220421	216.203481168436	215.487940200193	220.074178421969	220.751574238673	222.756467917525	221.818964797526	222.998857339833	222.872170865111	222.839772030379	221.941340642337	222.091276292141	220.602897752368	220.083891981177	221.087336210075	219.084523272366	219.616489271694	219.951882869600	223.378661777752	220.781944846607	221.382122018680	221.004036411375	218.717933088026	220.913207207220	219.192594807860	218.774441372802	222.281040781790	220.533925732253	217.174904341551	219.461046884719	218.207259332375	219.890267798853	221.501991820929	220.437353951677	221.232484865596	219.118661137933	219.788040702882	216.446360354010	214.217824784059	213.391652157096	216.218479365377	218.983576383978	218.989480732034	217.333174194161	217.479705665601	219.360647886929	218.620978171091	216.736713946125	216.713514888252	216.092712416379	216.261379704101	210.994063306013	210.857811560259	209.303202532702	211.771148287758	210.978343592164	213.276508750240	210.501221880055	207.693138985417	209.810829536718	209.844619419901	211.963394990806	210.647249734873	209.093003973460	208.958538812854	212.343405027076	208.339616410923	208.925689291596	206.814851694271	209.470441427801	206.673898550579	205.444581105701	208.633955146614	209.730182990317	210.403926210173	209.364296809455	208.704305585938	207.302882771106	209.828846811624	208.876000602703	206.588483524627	204.086909968434	206.957070758468	205.233648143550	204.411645475234	205.069710595483	206.063391077454	204.822077437225	206.038501941440	205.919231730590	207.517065944098	203.366893033324	205.056126464865	202.689775477844	196.857804501638	198.832608222580	198.931061416994	201.683703276349	200.336730434298	200.703125515309	199.567792821748	198.766965912754	199.358090883751	199.533570230743	198.211302341491	200.652330841108	198.191412195961	199.507375109119	195.782955409979	195.947345563929	196.759711619498	197.774049959034	198.488421772990	198.392971312765	198.022885652548	195.107469077695	199.049308597828	201.221273744984	199.660116618769	198.531799052229	198.861785653617	195.577644214311	193.820095001116	192.368692623486	195.700911360449	195.229939349216	196.292014764797	197.936195613241	193.775717957057	193.992070788241	195.183848562101	195.330863635762	197.915690243829	197.576772136706	196.400491147143	190.892534395809	192.980987692616	195.147660359887	194.476658311416	195.425385223326	190.769950792248	190.126436728615	191.783182290110	190.346204019544	191.190846024720	194.112783042047	194.372466054851	193.748266538173	190.416875893268	191.226723129643	190.227427229528	190.442999504260	189.904060635841	195.842046738758	193.380181440116	193.016467282141	193.151903610028	190.973072353171	191.810862162817	192.555605253238	195.423190330614	195.769059237065	197.280108882607	197.990866906841	194.755874864438	194.705944667115	195.506263998279	193.377037636192	194.072022770456	196.748102507181	196.417965616305	197.325587608129	198.465380237250	198.643335408342	197.796028311472	199.996704895682	201.386016402395	200.704522759704	205.240450130547	207.814765566989	206.912405036922	207.970422327615	210.687534775725	204.952947128515	203.369984258410	204.778418187406	205.084684955149	206.736862917829	204.438196115383	205.517091977712	207.768204729280	208.819389654530	211.458152302441	211.974142369539	213.835751895244	217.569038443496	215.532766615521	216.886892221131	221.352312934118	222.292175062940	222.189675850303	220.311422347634	222.473528284432	221.744077933072	221.444179760595	220.474182167476	224.064281036210	224.989642245611	221.335345964925	225.243069571471	227.406737895454	225.814662297901	223.287165744261	226.237466505626	228.765207623903	227.583878362274	226.913886294507	231.866846507520	235.822988572193	235.209681499181	241.351480184328	249.642561762025	258.936052896813	269.180003059469	278.208839901288	279.443136765478	279.846277935693	280.485953948353	281.853381774720	287.809002881452	287.567603149059	288.172191103207	288.130491364099	291.850492088294	291.703226297144	291.134770435029	292.017288496218	293.439501079433	295.052535565072	298.884172029354	297.877960376255	301.244892875337	297.775611398943	299.414962931691	299.530378316365	298.716932614082	299.978232919451	299.724397444438	300.505782969196	298.613181424995	303.158507487357	304.129226660128	311.181572156276	311.634754141903	313.856803028876	315.240802857077	316.109994587314	317.384181086347	318.738231145404	318.262111242244	321.845014813142	320.129027278465	320.163159764249	320.524342746575	320.566153865167	318.284209366667	317.880274690693	316.210319932346	315.325628362179	316.579802325698	314.954504731410	317.262294536344	314.916091671535	313.513034374798	312.879874549086	314.972113732493	309.889677550811	311.324503313644	312.015507218344	314.202488609661	316.013126092879	324.640171875254	332.019198024753	336.370624538462	338.269121978083	337.307918579789	336.205453603328	340.150095787383	336.669398127817	337.944846319192	332.700613983629	333.827667442708	334.460142360359	331.071054846537	331.113328573500	331.332394540695	330.754522429257	330.181056261429	329.452179977913	329.932757709668	326.097185669996	325.799682542109	327.210765996011	323.758218302919	324.972285505092	323.965809973514	323.240076098251	323.854760486322";
		String[] strs=str.split("\\s+");
		List<String> items=new ArrayList<String>();
		
		for(String item:strs){
			items.add(item);
			//System.out.println(item);
		}
		
		di.setData(items);
		WaveletOutlierDetection detection=new WaveletOutlierDetection();
		detection.setDi(di);
		detection.getPredictItems();
	}

}
