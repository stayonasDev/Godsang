package godsang.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIntakeHistory is a Querydsl query type for IntakeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntakeHistory extends EntityPathBase<IntakeHistory> {

    private static final long serialVersionUID = 554396418L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIntakeHistory intakeHistory = new QIntakeHistory("intakeHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> intakeType = createDateTime("intakeType", java.time.LocalDateTime.class);

    public final EnumPath<MealType> mealType = createEnum("mealType", MealType.class);

    public final QMember member;

    public QIntakeHistory(String variable) {
        this(IntakeHistory.class, forVariable(variable), INITS);
    }

    public QIntakeHistory(Path<? extends IntakeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIntakeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIntakeHistory(PathMetadata metadata, PathInits inits) {
        this(IntakeHistory.class, metadata, inits);
    }

    public QIntakeHistory(Class<? extends IntakeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

