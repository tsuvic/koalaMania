@Controller
@RequestMapping("/")
public class AnimalController {
	private final AnimalService animalService;
	private final PostImageService postImageService;
	private final ZooHistoryValidator zooHistoryValidator;
	
	@Autowired
	public AnimalController(AnimalService animalService,PostImageService postImageService, ZooHistoryValidator zooHistoryValidator) {
		this.animalService = animalService;
		this.postImageService = postImageService;
		this.zooHistoryValidator = zooHistoryValidator;
	}
}