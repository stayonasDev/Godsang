package godsang.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIntakeFood is a Querydsl query type for IntakeFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntakeFood extends EntityPathBase<IntakeFood> {

    private static final long serialVersionUID = -1573794640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIntakeFood intakeFood = new QIntakeFood("intakeFood");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QFood food;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIntakeHistory intakeHistory;

    public QIntakeFood(String variable) {
        this(IntakeFood.class, forVariable(variable), INITS);
    }

    public QIntakeFood(Path<? extends IntakeFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIntakeFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIntakeFood(PathMetadata metadata, PathInits inits) {
        this(IntakeFood.class, metadata, inits);
    }

    public QIntakeFood(Class<? extends IntakeFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.food = inits.isInitialized("food") ? new QFood(forProperty("food")) : null;
        this.intakeHistory = inits.isInitialized("intakeHistory") ? new QIntakeHistory(forProperty("intakeHistory"), inits.get("intakeHistory")) : null;
    }

}

